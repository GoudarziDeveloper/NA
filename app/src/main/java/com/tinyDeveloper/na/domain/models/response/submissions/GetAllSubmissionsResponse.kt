package com.tinyDeveloper.na.domain.models.response.submissions

data class GetAllSubmissionsResponse(
    val message: String,
    val status: Boolean,
    val submissions: List<Submission>
)