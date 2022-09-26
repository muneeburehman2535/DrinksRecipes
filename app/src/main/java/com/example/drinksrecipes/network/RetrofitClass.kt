package com.example.drinksrecipes.network

import com.example.drinksrecipes.utilities.AppGlobal
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClass {


    companion object{


        private var homeInstance: RetrofitClass? = null
        private var homeService: NetworkRequest? = null


        fun getHomeInstance(): RetrofitClass? {
            if (homeInstance == null) {
                homeInstance = RetrofitClass()
                val client = OkHttpClient.Builder().connectTimeout(40, TimeUnit.SECONDS).readTimeout(40, TimeUnit.SECONDS).writeTimeout(40, TimeUnit.SECONDS).build().newBuilder()
                val loggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                client.addInterceptor(loggingInterceptor)
                val loginRetrofit: Retrofit = Retrofit.Builder().baseUrl(AppGlobal.HOME_BASE_URL).client(client.build()).addConverterFactory(
                    GsonConverterFactory.create()).build()
                homeService = loginRetrofit.create(NetworkRequest::class.java)
                //homeService = loginRetrofit.create(WebRequestGeo::class.java)
            }
            return homeInstance
        }
    }

    fun getHomeRequestsInstance(): NetworkRequest? {
        return homeService
    }
}