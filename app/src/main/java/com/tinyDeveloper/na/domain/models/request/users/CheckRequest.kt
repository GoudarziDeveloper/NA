package com.tinyDeveloper.na.domain.models.request.users

data class CheckRequest(
    val phone: String,
    val code: String
)