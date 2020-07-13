package com.cindy.cindypodcasttest.Retrofit

import com.cindy.cindypodcasttest.Model.CastDetailModel
import retrofit2.Call
import retrofit2.http.GET

class GetCastDetail {

    interface ApiService{
        @GET("getcastsdetail")
        fun getCastDetail(): Call<CastDetailModel>
    }

}