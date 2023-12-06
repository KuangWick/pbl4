package com.ssn.studentapp.api

import com.ssn.studentapp.data.ClassResult
import com.ssn.studentapp.data.FacultyResult
import com.ssn.studentapp.data.SchoolResult
import com.ssn.studentapp.data.StudentResult
import retrofit2.Call
import retrofit2.http.GET

interface StringAPI {
    @GET("/Api/Faculty")
    fun getAllFaculty(): Call<FacultyResult>

    @GET("/Api/SChool")
    fun getAllSchool(): Call<SchoolResult>

    @GET("/Api/Class")
    fun getAllClass(): Call<ClassResult>
    @GET("/Api/Student")
    fun getAllStudent(): Call<StudentResult>
}