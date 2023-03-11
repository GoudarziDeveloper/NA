package com.tinyDeveloper.na.domain.models.response.jobs.get_all

data class GetAllJobsResponse(
    val jobs: List<Job>,
    val message: String,
    val status: Boolean
)