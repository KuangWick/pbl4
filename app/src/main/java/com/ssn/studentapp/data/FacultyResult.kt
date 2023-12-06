package com.ssn.studentapp.data

import com.ssn.studentapp.models.FacultyModel

data class FacultyResult(
    val message: String,
    val AllFaculty: List<FacultyModel>,
    val request: RequestModel ,
)
{

}

