package com.tinyDeveloper.na.domain.models.response.notifications

data class Notification(
    val baseId: String,
    val date: String,
    val description: String,
    val id: String,
    val image: String,
    val maxShowTime: String,
    var status: String,
    val title: String
)