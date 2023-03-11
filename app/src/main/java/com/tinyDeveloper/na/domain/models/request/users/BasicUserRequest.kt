package com.tinyDeveloper.na.domain.models.request.users

data class BasicUserRequest(
    val adminPassword: String,
    val adminPhone: String,
    val phone: String
)