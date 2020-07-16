package com.cindy.cindypodcasttest.retrofit

import com.cindy.cindypodcasttest.model.CastDetailModel
import retrofit2.Call
import retrofit2.http.GET

class GetCastDetail {

    interface ApiService{
        @GET("getcastdetail")
        fun getCastDetail(): Call<CastDetailModel>
    }

}