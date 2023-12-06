package com.ssn.studentapp.api

import com.ssn.studentapp.midleware.AuthorizationInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://192.168.1.2:4000/"

    private var mInstance: RetrofitClient? = null
    private val retrofit: Retrofit by lazy {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val httpClient = OkHttpClient.Builder()
            .addInterceptor(AuthorizationInterceptor())
            .addInterceptor(loggingInterceptor).build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
    }


    @Synchronized
    fun getInstance(): RetrofitClient {
        if (mInstance == null) {
            mInstance = RetrofitClient
        }
        return mInstance!!
    }

    fun getApi(): API {
        return retrofit.create(API::class.java)
    }

    fun getStringApi(): StringAPI {
        return retrofit.create(StringAPI::class.java)
    }
}
