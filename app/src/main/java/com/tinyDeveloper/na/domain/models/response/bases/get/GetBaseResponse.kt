package com.tinyDeveloper.na.domain.models.response.bases.get

data class GetBaseResponse(
    val base: Base,
    val message: String,
    val status: Boolean
)