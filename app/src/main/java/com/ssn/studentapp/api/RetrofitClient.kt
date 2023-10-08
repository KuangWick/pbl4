
package com.ssn.studentapp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://192.168.1.3:4000"
    private var mInstance: RetrofitClient? = RetrofitClient
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    @Synchronized
        fun getInstance(): RetrofitClient {
            if (mInstance == null) {
                mInstance = RetrofitClient
            }
            return mInstance as RetrofitClient
        }
    fun getApi(): API {
        return retrofit.create(API::class.java)
    }
}
