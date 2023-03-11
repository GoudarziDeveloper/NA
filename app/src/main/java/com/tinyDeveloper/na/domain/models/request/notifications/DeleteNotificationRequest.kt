package com.tinyDeveloper.na.domain.models.request.notifications

data class DeleteNotificationRequest(
    val phone: String,
    val password: String,
    val id: String
)
