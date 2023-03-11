package com.tinyDeveloper.na.domain.models.response.submissions

data class Submission(
    val baseName: String,
    val date: String,
    val description: String,
    val firstName: String,
    val id: String,
    val jobId: String,
    val lastName: String,
    val score: String,
    val status: String,
    val userId: String,
    val image: String
)