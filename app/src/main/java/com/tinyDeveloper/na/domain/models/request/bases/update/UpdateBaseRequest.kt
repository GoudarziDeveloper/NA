package com.tinyDeveloper.na.domain.models.request.bases.update

data class UpdateBaseRequest(
    val address: String,
    val baseName: String,
    val centerId: String,
    val commanderName: String,
    val commanderPhone: String,
    val id: String,
    val password: String,
    val phone: String,
    val status: String
)