package com.tinyDeveloper.na.utils

import com.tinyDeveloper.na.utils.Constants.PART_NAME

enum class UsersStatusValues(val value: String, val title: String){
    ACTIVE(value = "active", title = "فعال"),
    HIDE(value = "hide", title = "پنهان"),
    DELETED(value = "deleted", title = "پاک شده"),
    MAN_POWER(value = "man_power", title = "نیروی انسانی $PART_NAME"),
    CENTER_MAN_POWER(value = "center_man_power", title = "نیروی انسانی ${Constants.CENTER_NAME}"),
    ADMIN(value = "admin", title = "${Constants.MANAGER_NAME} ${Constants.CENTER_NAME}"),
    CYBER_MAN(value = "cyber_man", title = "فضای مجازی"),
    SUBSTITUTE(value = "substitute", title = "جانشین"),
    MANAGER_OF_PART(value = "manager_of_part", title = "${Constants.MANAGER_NAME} $PART_NAME"),
    SUPPORT_MAN(value = "support_man", title = "پشتیبان"),
}