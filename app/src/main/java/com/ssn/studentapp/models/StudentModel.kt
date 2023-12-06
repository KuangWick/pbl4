package com.ssn.studentapp.models

import com.google.gson.annotations.SerializedName

data class StudentModel(
    @SerializedName("MaSv")
    val MaSv: String,
    @SerializedName("HoTenSv")
    val HoTenSv: String,
    @SerializedName("MaLop")
    val MaLop: String,
    @SerializedName("School")
    val School: String,
    @SerializedName("DiaChi")
    val DiaChi: String,
    @SerializedName("Ngaysinh")
    val NgaySinh: String,
    @SerializedName("Gioitinh")
    val Gioitinh: String,
    @SerializedName("Email")
    val Email: String,
)
{

}
