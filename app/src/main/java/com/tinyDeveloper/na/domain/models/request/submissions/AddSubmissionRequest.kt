package com.tinyDeveloper.na.domain.models.request.submissions

data class AddSubmissionRequest(
    val description: String,
    val jobId: String,
    val password: String,
    val phone: String,
    val score: String,
    val status: String,
    val userId: String
)