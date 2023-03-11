package com.tinyDeveloper.na.domain.models.response.users.get_profile

import com.tinyDeveloper.na.domain.models.response.common.Base

data class GetProfileResponse(
    val base: Base,
    val message: String,
    val status: Boolean,
    val user: User
)