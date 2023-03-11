package com.tinyDeveloper.na.domain.models.response.files

data class File(
    val date: String,
    val id: String,
    val jobId: String,
    val src: String,
    val status: String,
    val submissionId: String
)