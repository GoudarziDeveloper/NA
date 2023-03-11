package com.tinyDeveloper.na.domain.models.request.users

import com.tinyDeveloper.na.utils.Constants.BASE_ID
import com.tinyDeveloper.na.utils.StateValues

data class User(
    val baseId: String = BASE_ID,
    val birthDay: String = "",
    val fatherName: String = "",
    val firstName: String = "",
    val image: String = "",
    val lastName: String = "",
    val nationalCode: String = "",
    val password: String = "",
    val phone: String = "",
    val status: String = StateValues.ACTIVE.value
)