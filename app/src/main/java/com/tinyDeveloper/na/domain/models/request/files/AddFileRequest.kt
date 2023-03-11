package com.tinyDeveloper.na.domain.models.request.files

data class AddFileRequest(
    val `file`: String,
    val jobId: String,
    val password: String,
    val phone: String,
    val status: String,
    val submissionId: String
)