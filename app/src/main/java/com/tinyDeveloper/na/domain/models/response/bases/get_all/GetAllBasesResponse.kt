package com.tinyDeveloper.na.domain.models.response.bases.get_all

data class GetAllBasesResponse(
    val bases: List<Base>,
    val message: String,
    val status: Boolean
)