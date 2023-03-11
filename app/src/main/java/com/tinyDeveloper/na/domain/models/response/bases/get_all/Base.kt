package com.tinyDeveloper.na.domain.models.response.bases.get_all

data class Base(
    val address: String,
    val baseName: String,
    val centerId: String,
    val commanderName: String,
    val commanderPhone: String,
    val date: String,
    val id: String,
    val score: Int,
    val status: String
)