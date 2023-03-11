package com.tinyDeveloper.na.domain.models.request.submissions

data class GetSubmissionRequest(
    val id: String,
    val password: String,
    val phone: String
)