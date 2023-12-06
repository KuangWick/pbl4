package com.ssn.studentapp.data

import com.ssn.studentapp.models.ClassModel

data class ClassResult(
    val message: String,
    val AllClass: List<ClassModel>,
    val request: RequestModel,
)
