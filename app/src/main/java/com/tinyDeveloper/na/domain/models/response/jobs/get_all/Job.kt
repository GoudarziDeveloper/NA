package com.tinyDeveloper.na.domain.models.response.jobs.get_all

data class Job(
    val centerId: String,
    val date: String,
    val description: String,
    val id: String,
    val maxShowTime: String,
    val status: String,
    val title: String,
    val complete: String,
    val completeStatus: String
)