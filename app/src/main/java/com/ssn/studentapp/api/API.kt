package com.ssn.studentapp.api

import com.ssn.studentapp.models.LoginResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface API {
    @FormUrlEncoded
    @POST("Api/NewUser")
    fun createUser(
        @Field("firstName") firstName: String,
        @Field("lastName") lastName: String,
        @Field("email") email: String,
        @Field("pwd") password: String
    ): Call<ResponseBody>

    @FormUrlEncoded
    @POST("Api/UserLogIn")
    fun userLogin(
        @Field("Email") email: String,
        @Field("Password") password: String
    ): Call<LoginResponse>


    @GET("Api//SingleUser/:id")
    fun getNotesById(
        @Query("_id") _id: String?
    ): Call<ArrayList<Unit>>

}