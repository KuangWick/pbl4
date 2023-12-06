package com.ssn.studentapp.models

import com.google.gson.annotations.SerializedName

data class SchoolModel(
    @SerializedName("name")
    val name: String,
    @SerializedName("code")
    val code: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("email")
    val email: String,
) {
}