package com.cindy.cindypodcasttest.api

import com.cindy.cindypodcasttest.model.CastDetailModel
import com.cindy.cindypodcasttest.model.CastModel

interface ApiCallBack {
    fun onCastCallbackDone(castModel: CastModel){}
    fun onCastDetailCallbackDone(castDetailModel: CastDetailModel){}
    fun onApiFailed(errorMessage: String?)
}