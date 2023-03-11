package com.tinyDeveloper.na.domain.models.response.notifications

data class NotificationsResponse(
    val message: String,
    val notifications: List<Notification>,
    val status: Boolean
)