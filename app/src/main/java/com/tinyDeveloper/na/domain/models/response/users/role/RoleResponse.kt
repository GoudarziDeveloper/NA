package com.tinyDeveloper.na.domain.models.response.users.role

import com.tinyDeveloper.na.domain.models.response.common.Base
import com.tinyDeveloper.na.domain.models.response.common.Role

data class RoleResponse(
    val bases: List<Base>,
    val message: String,
    val roles: Role?,
    val status: Boolean,
    val chatInfo: ChatInfo?,
    val chat_app_key: String?
)