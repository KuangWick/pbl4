package com.ssn.studentapp.api

import com.ssn.studentapp.data.LoginRequest
import com.ssn.studentapp.data.LoginResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST


interface API {

    //User
    @FormUrlEncoded
    @POST("/Api/NewUser")
    fun createUser(
        @Field("firstName") firstName: String,
        @Field("lastName") lastName: String,
        @Field("email") email: String,
        @Field("pwd") password: String,
    ): Call<ResponseBody>


    @POST("/Api/UserLogIn")
    fun userLogin(
        @Body request: LoginRequest,
    ): Call<LoginResponse>

}