package com.tinyDeveloper.na.domain.models.request.notifications

import com.tinyDeveloper.na.utils.Constants.BASE_ID
import com.tinyDeveloper.na.utils.StateValues
import com.tinyDeveloper.na.utils.TimeValues

data class AddNotificationRequest(
    val baseId: String = BASE_ID,
    val description: String = "",
    val image: String = "",
    val maxShowTime: String = TimeValues.ONE_DEY.value,
    val password: String = "",
    val phone: String = "",
    val status: String = StateValues.ACTIVE.value,
    val title: String = ""
)