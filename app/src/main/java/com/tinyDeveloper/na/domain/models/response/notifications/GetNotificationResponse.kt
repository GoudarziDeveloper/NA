package com.tinyDeveloper.na.domain.models.response.notifications

data class GetNotificationResponse(
    val message: String,
    val notification: Notification,
    val status: Boolean
)