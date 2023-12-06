package com.ssn.studentapp.data

import com.ssn.studentapp.models.StudentModel

data class StudentResult(
    val message: String,
    val AllStudent: List<StudentModel>,
    val request: RequestModel,
) {

}
