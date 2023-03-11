package com.tinyDeveloper.na.utils

enum class AdvanceStateValues(val value: String, val title: String) {
    ACTIVE(value = "active", title = "فعال"),
    WAITING(value = "waiting", title = "در صف برسی"),
    ACCEPTED(value = "accepted", title = "تایید شده"),
    ACCEPTABLE(value = "acceptable", title = "قابل قبول"),
    FAILED(value = "failed", title = "رد شده"),
    HIDE(value = "hide", title = "پنهان"),
    DELETED(value = "deleted", title = "پاک شده")
}