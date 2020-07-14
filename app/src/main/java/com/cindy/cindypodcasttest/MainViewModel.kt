package com.cindy.cindypodcasttest

import androidx.lifecycle.*
import com.cindy.cindypodcasttest.Model.CastDetailModel
import com.cindy.cindypodcasttest.Model.CastModel
import kotlinx.coroutines.launch

class MainViewModel(private val mRepository: ApiRepository): ViewModel() {

    var mCastLiveData: MutableLiveData<CastModel> = MutableLiveData()
    val mCastEmpty: LiveData<Boolean> = Transformations.map(mCastLiveData){
        it==null || it.castData?.podcast?.isNullOrEmpty()!!
    }

    var mCastDetailLiveData: MutableLiveData<CastDetailModel> = MutableLiveData()
    val mCastDetailEmpty: LiveData<Boolean> = Transformations.map(mCastDetailLiveData){
        it==null || it.data?.collection?.contentFeed?.isNullOrEmpty()!!
    }

    var mErrorMessageLiveData: MutableLiveData<String> = MutableLiveData()

    init {
        getCastData()
    }

    fun getCastData(){
        mRepository.callGetCast(object: ApiCallBack{
            override fun onCastCallbackDone(castModel: CastModel) {
                super.onCastCallbackDone(castModel)
            }
            override fun onApiFailed(errorMessage: String?) {
                viewModelScope.launch {
                    mErrorMessageLiveData.value = errorMessage
                }
            }
        })
    }

    fun getCastDetailData(){
        mRepository.callGetCast(object: ApiCallBack{
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