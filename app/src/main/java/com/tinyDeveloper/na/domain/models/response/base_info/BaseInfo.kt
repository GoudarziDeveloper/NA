package com.tinyDeveloper.na.domain.models.response.base_info

data class BaseInfo(
    val appEnabled: String,
    val checkCodeUrl: String,
    val id: String,
    val lastVersion: String,
    val messageEnabled: String,
    val messageText: String,
    val messageTitle: String,
    val minVersion: String,
    val sendCodeUrl: String,
    val status: String,
    val stopText: String,
    val stopTitle: String,
    val updateLink: String,
    val updateText: String,
    val updateTitle: String,
    val uploadUrl: String,
    val isSnow: String,
    val manager: String,
    val part: String,
    val center: String
)