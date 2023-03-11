package com.tinyDeveloper.na.domain.models.response.users.get_all

data class GetAllUsersResponse(
    val message: String,
    val status: Boolean,
    val users: List<User>
)