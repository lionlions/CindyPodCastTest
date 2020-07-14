package com.cindy.cindypodcasttest.retrofit

import com.cindy.cindypodcasttest.model.CastModel
import retrofit2.Call
import retrofit2.http.GET

class GetCast {

    interface ApiService{
        @GET("getcasts")
        fun getCast(): Call<CastModel>
    }

}