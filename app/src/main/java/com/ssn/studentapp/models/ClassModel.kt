package com.ssn.studentapp.models

import com.google.gson.annotations.SerializedName

data class ClassModel(
    @SerializedName("MaLop")
    val MaLop: String,
    @SerializedName("TenLop")
    val TenLop: String,
    @SerializedName("MaKhoa")
    val MaKhoa: String,
)
{

}
