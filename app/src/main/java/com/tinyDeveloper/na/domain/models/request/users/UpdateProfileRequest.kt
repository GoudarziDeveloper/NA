package com.tinyDeveloper.na.domain.models.request.users

data class UpdateProfileRequest(
    val phone: String,
    val password: String,
    val image: String
)
