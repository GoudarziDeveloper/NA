package com.tinyDeveloper.na.domain.models.request.notifications

data class UpdateNotificationRequest(
    val baseId: String,
    val description: String,
    val image: String,
    val maxShowTime: String,
    val password: String,
    val phone: String,
    val status: String,
    val title: String,
    val id: String
)
