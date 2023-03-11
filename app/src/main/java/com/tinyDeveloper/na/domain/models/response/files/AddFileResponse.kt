package com.tinyDeveloper.na.domain.models.response.files

data class AddFileResponse(
    val `file`: File,
    val message: String,
    val status: Boolean
)