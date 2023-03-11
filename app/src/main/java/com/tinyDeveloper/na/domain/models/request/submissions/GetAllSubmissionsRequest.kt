package com.tinyDeveloper.na.domain.models.request.submissions

data class GetAllSubmissionsRequest(
    val jobId: String,
    val password: String,
    val phone: String
)