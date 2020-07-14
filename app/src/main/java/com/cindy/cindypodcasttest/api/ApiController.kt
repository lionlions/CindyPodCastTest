package com.cindy.cindypodcasttest.api

import android.util.Log
import com.cindy.cindypodcasttest.BuildConfig
import com.cindy.cindypodcasttest.model.CastDetailModel
import com.cindy.cindypodcasttest.model.CastModel
import com.cindy.cindypodcasttest.retrofit.GetCast
import com.cindy.cindypodcasttest.retrofit.GetCastDetail
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiController {

    private val TAG: String = javaClass.simpleName

    companion object {
        private var mApiController: ApiController? = null
        fun getInstance(): ApiController? {
            if (mApiController == null) {
                mApiController =
                    ApiController()
            }
            return mApiController
        }
    }

    fun createRetrofit(url: String?): Retrofit? {
        // Check null
        if(url.isNullOrEmpty()){
            if(BuildConfig.DEBUG)Log.e(TAG, "url should not be null or empty")
            return null
        }

        var okHttpBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val logging: HttpLoggingInterceptor = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            okHttpBuilder.addInterceptor(logging).build()
        }
        val client: OkHttpClient = okHttpBuilder.build()
        return Retrofit.Builder()
                        .baseUrl(url)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(client)
                        .build()
    }

    fun getCast(url: String?, callback: Callback<CastModel>) {
        if(BuildConfig.DEBUG)Log.v(TAG, "===== getCast =====")
        val retrofit: Retrofit? = createRetrofit(url)
        if(retrofit!=null){
            val apiService: GetCast.ApiService = retrofit.create(
                GetCast.ApiService::class.java)
            val call: Call<CastModel> = apiService.getCast()
            call.enqueue(callback)
        }else{
            if(BuildConfig.DEBUG)Log.e(TAG, "Something went wrong!! Retrofit is null!!")
        }
    }

    fun getCastDetail(url: String?, callback: Callback<CastDetailModel>) {
        if(BuildConfig.DEBUG)Log.v(TAG, "===== getCast =====")
        val retrofit: Retrofit? = createRetrofit(url)
        if(retrofit!=null){
            val apiService: GetCastDetail.ApiService = retrofit.create(
                GetCastDetail.ApiService::class.java)
            val call: Call<CastDetailModel> = apiService.getCastDetail()
            call.enqueue(callback)
        }else{
            if(BuildConfig.DEBUG)Log.e(TAG, "Something went wrong!! Retrofit is null!!")
        }
    }

}