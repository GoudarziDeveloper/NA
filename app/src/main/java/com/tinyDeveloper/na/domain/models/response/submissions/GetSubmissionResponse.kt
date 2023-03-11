package com.tinyDeveloper.na.domain.models.response.submissions

import com.tinyDeveloper.na.domain.models.response.files.File

data class GetSubmissionResponse(
    val files: List<File>,
    val message: String,
    val status: Boolean,
    val submission: SubmissionX
)