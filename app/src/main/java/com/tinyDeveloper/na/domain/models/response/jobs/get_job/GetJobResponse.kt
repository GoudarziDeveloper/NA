package com.tinyDeveloper.na.domain.models.response.jobs.get_job

import com.tinyDeveloper.na.domain.models.response.files.File

data class GetJobResponse(
    val files: List<File>,
    val job: Job,
    val message: String,
    val status: Boolean
)