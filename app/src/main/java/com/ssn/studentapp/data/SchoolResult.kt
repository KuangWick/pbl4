package com.ssn.studentapp.data

import com.ssn.studentapp.models.SchoolModel

data class SchoolResult(
    val message: String,
    val AllSchool: List<SchoolModel>,
    val request: RequestModel ,
) {

}
