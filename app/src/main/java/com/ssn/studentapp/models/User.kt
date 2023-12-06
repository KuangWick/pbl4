package com.ssn.studentapp.models

import com.google.gson.annotations.SerializedName


data class User(
    @SerializedName("id")
    val id: String,
    @SerializedName("firstName")
    val firstName: String,
    @SerializedName("lastName")
    val lastName: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("pwd")
    val pwd: String,
    val email_verified_at: Any,
    val access_token: String?,
)