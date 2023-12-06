package com.ssn.studentapp.models

import com.google.gson.annotations.SerializedName

data class FacultyModel(
    @SerializedName("MaKhoa")
    val maKhoa: String,
    @SerializedName("TenKhoa")
    val tenKhoa: String,
)  {
}