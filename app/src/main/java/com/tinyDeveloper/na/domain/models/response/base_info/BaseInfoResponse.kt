package com.tinyDeveloper.na.domain.models.response.base_info

data class BaseInfoResponse(
    var baseInfo: BaseInfo,
    val message: String,
    val status: Boolean
)