package com.cindy.cindypodcasttest

import com.cindy.cindypodcasttest.Model.CastDetailModel
import com.cindy.cindypodcasttest.Model.CastModel

interface ApiCallBack {
    fun onCastCallbackDone(castModel: CastModel){}
    fun onCastDetailCallbackDone(castDetailModel: CastDetailModel){}
    fun onApiFailed(errorMessage: String?)
}