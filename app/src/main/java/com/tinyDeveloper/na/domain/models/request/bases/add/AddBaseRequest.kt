package com.tinyDeveloper.na.domain.models.request.bases.add

data class AddBaseRequest(
    val address: String,
    val baseName: String,
    val centerId: String,
    val commanderName: String,
    val commanderPhone: String,
    val password: String,
    val phone: String,
    val status: String
)