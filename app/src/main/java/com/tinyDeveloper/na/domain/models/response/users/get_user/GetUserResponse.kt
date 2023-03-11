package com.tinyDeveloper.na.domain.models.response.users.get_user

data class GetUserResponse(
    val message: String,
    val roles: Roles,
    val status: Boolean,
    val user: User
)