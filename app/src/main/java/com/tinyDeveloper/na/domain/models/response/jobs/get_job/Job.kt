package com.tinyDeveloper.na.domain.models.response.jobs.get_job

data class Job(
    val centerId: String,
    val date: String,
    val description: String,
    val id: String,
    val maxShowTime: String,
    val status: String,
    val title: String
)