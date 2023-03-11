package com.tinyDeveloper.na.domain.models.request.notifications

data class GetNotificationRequest(
    val id: String,
    val password: String,
    val phone: String
)