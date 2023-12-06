package com.ssn.studentapp.midleware

import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Request

class AuthorizationInterceptor() : Interceptor {
    private val JWT_KEY = "mastereventsisthekey"
    override fun intercept(chain: Chain): okhttp3.Response {
        val originalRequest: Request = chain.request()
        val requestBuilder: Request.Builder =
            originalRequest.newBuilder().addHeader("Authorization","Bearer $JWT_KEY")
        val newRequest: Request = requestBuilder.build()
        return chain.proceed(newRequest)
    }
}