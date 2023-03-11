package com.tinyDeveloper.na.domain.models.response.submissions

data class AddSubmissionResponse(
    val message: String,
    val status: Boolean,
    val submission: SubmissionX?
)