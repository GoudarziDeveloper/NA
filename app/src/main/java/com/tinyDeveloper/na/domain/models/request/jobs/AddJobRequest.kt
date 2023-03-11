package com.tinyDeveloper.na.domain.models.request.jobs

import com.tinyDeveloper.na.utils.StateValues
import com.tinyDeveloper.na.utils.TimeValues

data class AddJobRequest(
    val centerId: String = "5",
    val description: String = "",
    val maxShowTime: String = TimeValues.ONE_DEY.value,
    val password: String = "",
    val phone: String = "",
    val status: String = StateValues.ACTIVE.value,
    val title: String = ""
)