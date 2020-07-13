package com.cindy.cindypodcasttest.Retrofit

import com.cindy.cindypodcasttest.Model.CastModel
import retrofit2.Call
import retrofit2.http.GET

class GetCast {

    interface ApiService{
        @GET("getcasts")
        fun getCast(): Call<CastModel>
    }

}