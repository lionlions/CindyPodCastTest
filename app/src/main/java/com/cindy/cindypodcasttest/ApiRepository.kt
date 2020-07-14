package com.cindy.cindypodcasttest

import com.cindy.cindypodcasttest.Model.CastDetailModel
import com.cindy.cindypodcasttest.Model.CastModel
import com.cindy.cindypodcasttest.Retrofit.ApiConfig
import com.cindy.cindypodcasttest.Retrofit.ApiController
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiRepository {

    private var mApiCallback: ApiCallBack? = null

    fun callGetCast(apiCallBack: ApiCallBack){
        mApiCallback = apiCallBack
        val apiController: ApiController? = ApiController.getInstance()
        if(apiController!=null){
            apiController.getCast(ApiConfig.URL_DOMAIN, object: Callback<CastModel>{
                override fun onFailure(call: Call<CastModel>, t: Throwable) {
                    returnErrorMessage(t.message)
                }

                override fun onResponse(call: Call<CastModel>, response: Response<CastModel>) {
                    if(response.isSuccessful){
                        if(response.body()!=null){
                            val castModel: CastModel? = response.body()
                            if(castModel!=null){
                                apiCallBack.onCastCallbackDone(castModel)
                            }
                        }else{
                            returnErrorMessage("Error Code: ${ApiConfig.ERROR_CODE_RESPONSE_BODY_IS_NULL}")
                        }
                    }else{
                        returnErrorMessage("Error Code: ${ApiConfig.ERROR_CODE_RESPONSE_FAILED}")
                    }
                }

            })
        }
    }

    fun callGetCastDetail(apiCallBack: ApiCallBack){
        mApiCallback = apiCallBack
        val apiController: ApiController? = ApiController.getInstance()
        if(apiController!=null){
            apiController.getCastDetail(ApiConfig.URL_DOMAIN, object: Callback<CastDetailModel>{
                override fun onFailure(call: Call<CastDetailModel>, t: Throwable) {
                    returnErrorMessage(t.message)
                }

                override fun onResponse(call: Call<CastDetailModel>, response: Response<CastDetailModel>) {
                    if(response.isSuccessful){
                        if(response.body()!=null){
                            val castDetailModel: CastDetailModel? = response.body()
                            if(castDetailModel!=null){
                                apiCallBack.onCastDetailCallbackDone(castDetailModel)
                            }
                        }else{
                            returnErrorMessage("Error Code: ${ApiConfig.ERROR_CODE_RESPONSE_BODY_IS_NULL}")
                        }
                    }else{
                        returnErrorMessage("Error Code: ${ApiConfig.ERROR_CODE_RESPONSE_FAILED}")
                    }
                }

            })
        }
    }

    fun returnErrorMessage(errorMessage: String?){
        mApiCallback?.run {
            onApiFailed(errorMessage)
        }
    }

}