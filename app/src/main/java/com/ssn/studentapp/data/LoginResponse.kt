package com.ssn.studentapp.data

import com.google.gson.annotations.SerializedName
import com.ssn.studentapp.models.User


data class LoginResponse(
    @SerializedName("isError") val isError: Boolean,
    @SerializedName("user") val user: User,
    @SerializedName("message") val message: String,


    ) {

}



