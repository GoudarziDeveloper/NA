package com.tinyDeveloper.na.domain.models.request.submissions

data class UpdateSubmissionRequest(
    val description: String,
    val id: String,
    val jobId: String,
    val password: String,
    val phone: String,
    val score: String,
    val status: String,
    val userId: String
)