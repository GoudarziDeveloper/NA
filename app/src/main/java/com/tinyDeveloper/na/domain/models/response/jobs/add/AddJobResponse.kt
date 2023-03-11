package com.tinyDeveloper.na.domain.models.response.jobs.add

data class AddJobResponse(
    val jobId: Int,
    val message: String,
    val status: Boolean
)