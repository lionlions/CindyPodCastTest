package com.cindy.cindypodcasttest.viewmodel

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.*
import com.cindy.cindypodcasttest.api.ApiCallBack
import com.cindy.cindypodcasttest.api.ApiRepository
import com.cindy.cindypodcasttest.model.*
import com.cindy.cindypodcasttest.model.Collection
import com.cindy.cindypodcasttest.view.CastPlayerActivity
import kotlinx.coroutines.launch

class CastDetailViewModel(private val mRepository: ApiRepository?): ViewModel() {

    private val TAG: String = javaClass.simpleName

    var isBackClick: MutableLiveData<Boolean> = MutableLiveData()
    var mCastDetailLiveData: MutableLiveData<Collection> = MutableLiveData()
    val isCastDetailNull: LiveData<Boolean> = Transformations.map(mCastDetailLiveData){
        it==null
    }
    var mContentFeed: MutableLiveData<List<ContentFeed>> = MutableLiveData()
    val isContentFeedEmpty: LiveData<Boolean> = Transformations.map(mContentFeed){
        it.isNullOrEmpty()
    }

    var mErrorMessageLiveData: MutableLiveData<String> = MutableLiveData()

    init {
        getCastDetailData()
    }

    fun getCastDetailData(){
        mRepository?.callGetCastDetail(object:
            ApiCallBack {
            override fun onCastDetailCallbackDone(castDetailModel: CastDetailModel) {
                viewModelScope.launch {
                    if(castDetailModel.data!=null
                        && castDetailModel.data!!.collection!=null){
                        mCastDetailLiveData.value = castDetailModel.data!!.collection!!
                        if(!castDetailModel.data!!.collection!!.contentFeed.isNullOrEmpty()){
                            mContentFeed.value = castDetailModel.data!!.collection!!.contentFeed!!
                        }
                    }
                }
            }
            override fun onApiFailed(errorMessage: String?) {
                mErrorMessageLiveData.value = errorMessage
            }
        })
    }

    fun onItemClick(view: View, collection: Collection, position: Int){
        //Go to cast player activity
        val context: Context = view.context
        var intent: Intent = Intent()
        intent.setClass(context, CastPlayerActivity::class.java)
        intent.putExtra("position", position)
        intent.putExtra("collection", collection)
        context.startActivity(intent)
    }

    fun onBackClick(view: View){
        isBackClick.value = true
    }

}