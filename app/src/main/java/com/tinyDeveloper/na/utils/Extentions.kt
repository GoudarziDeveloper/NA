package com.tinyDeveloper.na.utils

import android.graphics.Bitmap
import android.util.Base64
import androidx.compose.ui.graphics.Color
import com.tinyDeveloper.na.domain.models.response.common.Base
import com.tinyDeveloper.na.domain.models.response.submissions.Submission
import com.tinyDeveloper.na.domain.models.response.users.get_all.User
import com.tinyDeveloper.na.ui.theme.*
import com.tinyDeveloper.na.utils.Constants.CENTER_NAME
import com.tinyDeveloper.na.utils.Constants.PART_NAME
import java.io.ByteArrayOutputStream

fun String?.toTimeValue(): TimeValues{
    return if(this.isNullOrEmpty()) TimeValues.ONE_DEY else {
        var timeValues = TimeValues.ONE_DEY
        TimeValues.values().forEach {
            if(it.value == this){
                timeValues = it
            }
        }
        timeValues
    }
}

fun String?.toStateValue(): StateValues {
    return if(this.isNullOrEmpty()) StateValues.ACTIVE else {
        var stateValues = StateValues.ACTIVE
        StateValues.values().forEach {
            if(this == it.value){
                stateValues = it
            }
        }
        stateValues
    }
}

fun String?.toUsersStatusValue(): UsersStatusValues {
    return if(this.isNullOrEmpty()) UsersStatusValues.ACTIVE else {
        var stateValues = UsersStatusValues.ACTIVE
        UsersStatusValues.values().forEach {
            if(this == it.value){
                stateValues = it
            }
        }
        stateValues
    }
}

fun String?.toAdvanceStateValue(): AdvanceStateValues {
    return if(this.isNullOrEmpty()) AdvanceStateValues.ACTIVE else {
        var stateValues = AdvanceStateValues.ACTIVE
        AdvanceStateValues.values().forEach {
            if(this == it.value){
                stateValues = it
            }
        }
        stateValues
    }
}

fun String?.toBase(bases: List<Base>?): Base{
    var base = Base(
        address = "",
        baseName = "همه $PART_NAME ها",
        centerId = "",
        commanderName = "",
        commanderPhone = "",
        date = "",
        id = "-1",
        status = ""
    )
    return if (this == null || bases == null) base else {
        bases.forEach{
            if (it.id == this)
                base = it
        }
        base
    }
}

fun String.toColor(): Color{
    return when(this){
        AdvanceStateValues.DELETED.value -> HighPriorityColor
        AdvanceStateValues.HIDE.value -> HidePriorityColor
        AdvanceStateValues.ACTIVE.value -> LowPriorityColor
        "normal" -> LowPriorityColor
        AdvanceStateValues.ACCEPTED.value -> LowPriorityColor
        AdvanceStateValues.ACCEPTABLE.value -> AcceptablePriorityColor
        AdvanceStateValues.FAILED.value -> HighPriorityColor
        AdvanceStateValues.WAITING.value -> WaitingPriorityColor
        "" -> MediumPriorityColor
        else -> NonePriorityColor
    }
}

fun String.toCompleteStatusColor(isAdmin: Boolean): Color{
    return if (isAdmin){
        when(this){
            "0" -> LowPriorityColor
            else -> WaitingPriorityColor
        }
    }else{
        when(this){
            "0" -> WaitingPriorityColor
            "" -> WaitingPriorityColor
            else -> LowPriorityColor
        }
    }

}

fun String.toSubmissionScoreColor(max: Int): Color{
    return try {
        val one = max / 4
        val two = (max / 4) * 2
        val three = (max / 4) * 3
        return when (this.toInt()) {
            in (three + 1)..max -> LowPriorityColor
            in (two + 1)..three -> AcceptablePriorityColor
            in (one + 1)..two -> MediumPriorityColor
            else -> HighPriorityColor
        }
    }catch (e: Exception){ NonePriorityColor }
}

fun List<Submission>.maxScore(): Int{
    return try {
        var max = 0
        for (item in this){
            if (item.score.toInt() > max)
                max = item.score.toInt()
        }
        max
    }catch (e: Exception) { 0 }
}

fun List<User>.max(): Int{
    return try {
        var max = 0
        for (item in this){
            if (item.score > max)
                max = item.score
        }
        max
    }catch (e: Exception) { 0 }
}

fun String.setStatus(): String{
    return when(this){
        UsersStatusValues.DELETED.value -> UsersStatusValues.DELETED.title
        UsersStatusValues.HIDE.value -> "غیر فعال"
        UsersStatusValues.ACTIVE.value -> UsersStatusValues.ACTIVE.title
        UsersStatusValues.MANAGER_OF_PART.value -> UsersStatusValues.MANAGER_OF_PART.title
        UsersStatusValues.SUBSTITUTE.value -> UsersStatusValues.SUBSTITUTE.title
        UsersStatusValues.SUPPORT_MAN.value -> UsersStatusValues.SUPPORT_MAN.title
        UsersStatusValues.ADMIN.value -> UsersStatusValues.ADMIN.title
        UsersStatusValues.MAN_POWER.value -> UsersStatusValues.MAN_POWER.title
        UsersStatusValues.CENTER_MAN_POWER.value-> UsersStatusValues.CENTER_MAN_POWER.title
        UsersStatusValues.CYBER_MAN.value -> UsersStatusValues.CYBER_MAN.title
        "normal" -> "معمولی"
        UsersStatusValues.ADMIN.value -> UsersStatusValues.ADMIN.title
        AdvanceStateValues.ACCEPTED.value -> AdvanceStateValues.ACCEPTED.title
        AdvanceStateValues.ACCEPTABLE.value -> AdvanceStateValues.ACCEPTABLE.title
        AdvanceStateValues.FAILED.value -> AdvanceStateValues.FAILED.title
        AdvanceStateValues.WAITING.value -> AdvanceStateValues.WAITING.title
        "center" -> CENTER_NAME
        "" -> "در انتظار تحویل"
        else -> "نا مشخص"
    }
}

fun Bitmap.bitmapToBase64(): String{
    val byteArrayOutputStream = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream)
    return "data:;base64,${Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT)}"
}

fun String.setCompleteAdmin(): String{
    return "$this مورد تحویل شده"
}

fun String.setCompleteUser(): String{
    return "وضعیت: ${this.setStatus()}"
}

fun String.setExtraTime(date: String): String{
    return try {
        val time = this.toLong() - ((System.currentTimeMillis() / 1000) - date.toLong())
        if (time > 86400)
            "${time / 86400} روز آینده "
        else if (time > 3600)
            "${time / 3600} ساعت آینده "
        else if (time > 60)
            "${time / 60} دقیقه آینده "
        else if (time >= 0)
            "لحظاتی دیگر"
        else "اتمام فرصت ارسال"
    }catch (e: Exception){
        "اتمام فرصت ارسال"
    }
}

fun String.toTime(): String{
    val different = (System.currentTimeMillis() / 1000) - this.toLong()
    return if (different > 86400)
        "${different / 86400} روز پیش "
    else if (different > 3600)
        "${different / 3600} ساعت پیش "
    else if (different > 60)
        "${different / 60} دقیقه پیش "
    else if (different >= 0)
        "لحظاتی پیش"
    else "لحظاتی پیش"
}