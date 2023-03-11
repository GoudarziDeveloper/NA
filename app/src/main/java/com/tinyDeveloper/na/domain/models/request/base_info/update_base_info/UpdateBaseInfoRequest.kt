package com.tinyDeveloper.na.domain.models.request.base_info.update_base_info

data class UpdateBaseInfoRequest(
    val appEnabled: String,
    val checkCodeUrl: String,
    val id: String,
    val lastVersion: String,
    val messageEnabled: String,
    val messageText: String,
    val messageTitle: String,
    val minVersion: String,
    val password: String,
    val phone: String,
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