package com.tinyDeveloper.na.domain.models.request.jobs

data class UpdateJobRequest(
    val centerId: String,
    val description: String,
    val id: String,
    val maxShowTime: String,
    val password: String,
    val phone: String,
    val status: String,
    val title: String
)