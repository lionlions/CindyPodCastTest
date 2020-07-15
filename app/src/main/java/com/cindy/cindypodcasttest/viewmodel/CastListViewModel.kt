package com.cindy.cindypodcasttest.viewmodel

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.databinding.BindingAdapter
import androidx.lifecycle.*
import com.cindy.cindypodcasttest.api.ApiCallBack
import com.cindy.cindypodcasttest.api.ApiRepository
import com.cindy.cindypodcasttest.model.CastDetailModel
import com.cindy.cindypodcasttest.model.CastModel
import com.cindy.cindypodcasttest.model.Podcast
import com.cindy.cindypodcasttest.view.CastDetailActivity
import kotlinx.coroutines.launch

class CastListViewModel(private val mRepository: ApiRepository): ViewModel() {

    private val TAG: String = javaClass.simpleName

    var mCastLiveData: MutableLiveData<List<Podcast>> = MutableLiveData()
    val isCastEmpty: LiveData<Boolean> = Transformations.map(mCastLiveData){
        it.isNullOrEmpty()
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

    fun onItemClick(view: View, id: String){
        //Go to next cast detail activity
        val context: Context = view.context
        var intent: Intent = Intent()
        intent.setClass(context, CastDetailActivity::class.java)
        context.startActivity(intent)
    }

}