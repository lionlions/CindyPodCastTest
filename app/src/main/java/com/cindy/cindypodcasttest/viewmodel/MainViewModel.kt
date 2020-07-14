package com.cindy.cindypodcasttest.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.cindy.cindypodcasttest.api.ApiCallBack
import com.cindy.cindypodcasttest.api.ApiRepository
import com.cindy.cindypodcasttest.model.CastDetailModel
import com.cindy.cindypodcasttest.model.CastModel
import com.cindy.cindypodcasttest.model.Podcast
import kotlinx.coroutines.launch

class MainViewModel(private val mRepository: ApiRepository): ViewModel() {

    private val TAG: String = javaClass.simpleName

    var mCastLiveData: MutableLiveData<List<Podcast>> = MutableLiveData()
    val isCastEmpty: LiveData<Boolean> = Transformations.map(mCastLiveData){
        it.isNullOrEmpty()
    }

    var mCastDetailLiveData: MutableLiveData<CastDetailModel> = MutableLiveData()
    val isCastDetailEmpty: LiveData<Boolean> = Transformations.map(mCastDetailLiveData){
        it==null || it.data?.collection?.contentFeed?.isNullOrEmpty()!!
    }

    var mErrorMessageLiveData: MutableLiveData<String> = MutableLiveData()

    init {
        getCastData()
    }

    fun getCastData(){
        Log.v(TAG, "===== getCastData =====")
        mRepository.callGetCast(object:
            ApiCallBack {
            override fun onCastCallbackDone(castModel: CastModel) {
                Log.v(TAG, "===== onCastCallbackDone =====")
                viewModelScope.launch {
                    Log.w(TAG, "launch!!")
                    if(castModel.data!=null
                        && !castModel.data!!.podcast.isNullOrEmpty()){
                        Log.i(TAG, "update value")
                        Log.w(TAG, "value: ${castModel.data!!.podcast}")
                        mCastLiveData.value = castModel.data!!.podcast
                    }
                }
            }
            override fun onApiFailed(errorMessage: String?) {
                viewModelScope.launch {
                    mErrorMessageLiveData.value = errorMessage
                }
            }
        })
    }

    fun getCastDetailData(){
        mRepository.callGetCast(object:
            ApiCallBack {
            override fun onCastDetailCallbackDone(castDetailModel: CastDetailModel) {
                viewModelScope.launch {
                    mCastDetailLiveData.value = castDetailModel
                }
            }
            override fun onApiFailed(errorMessage: String?) {
                mErrorMessageLiveData.value = errorMessage
            }
        })
    }

}