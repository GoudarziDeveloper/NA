package com.tinyDeveloper.na.utils

enum class TimeValues(val value: String, val title: String) {
    ONE_DEY(value = (60 * 60 * 24).toString(), title = "یک روز"),
    TOW_DAYS(value = (60 * 60 * 24 * 2).toString(), title = "دو روز"),
    THREE_DAYS(value = (60 * 60 * 24 * 3).toString(), title = "سه روز"),
    FOR_DAYS(value = (60 * 60 * 24 * 4).toString(), title = "چهار روز"),
    FIVE_DAYS(value = (60 * 60 * 24 * 5).toString(), title = "پنج روز"),
    SIX_DAYS(value = (60 * 60 * 24 * 6).toString(), title = "شیش روز"),
    ONE_WEEK(value = (60 * 60 * 24 * 7).toString(), title = "یک هفته"),
    EIGHT_DAYS(value = (60 * 60 * 24 * 8).toString(), title = "هشت روز"),
    NINE_DAYS(value = (60 * 60 * 24 * 7 * 9).toString(), title = "نه روز"),
    TEN_DAYS(value = (60 * 60 * 24 * 7 * 10).toString(), title = "ده روز"),
    ONE_MONTH(value = (60 * 60 * 24 * 7 * 4).toString(), title = "یک ماه"),
    TWO_MONTH(value = (60 * 60 * 24 * 7 * 4 * 2).toString(), title = "دو ماه"),
    THREE_MONTH(value = (60 * 60 * 24 * 7 * 4 * 3).toString(), title = "سه ماه"),
    FOUR_MONTH(value = (60 * 60 * 24 * 7 * 4 * 4).toString(), title = "چهار ماه"),
    FIVE_MONTH(value = (60 * 60 * 24 * 7 * 4 * 5).toString(), title = "پنج ماه"),
    SIX_MONTH(value = (60 * 60 * 24 * 7 * 4 * 6).toString(), title = "شش ماه"),
    ONE_YEAR(value = (60 * 60 * 24 * 7 * 4 * 12).toString(), title = "یک سال")
}