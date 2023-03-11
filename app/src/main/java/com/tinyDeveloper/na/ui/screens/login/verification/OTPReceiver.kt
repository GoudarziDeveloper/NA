package com.tinyDeveloper.na.ui.screens.login.verification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.auth.api.phone.SmsRetrieverClient
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status

class OTPReceiver : BroadcastReceiver() {
    private var otpReceiveListener: OTPReceiveListener? = null

    fun init(otpReceiveListener: OTPReceiveListener?) {
        this.otpReceiveListener = otpReceiveListener
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (SmsRetriever.SMS_RETRIEVED_ACTION == intent?.action) {
            val extras: Bundle? = intent.extras
            val status: Status = extras?.get(SmsRetriever.EXTRA_STATUS) as Status

            when (status.statusCode) {
                CommonStatusCodes.SUCCESS -> {
                    val msg: String? = extras.getString(SmsRetriever.EXTRA_SMS_MESSAGE)

                    val smsCode = msg?.let { "[0-9]{6}".toRegex().find(it) }
                    smsCode?.value?.let { otpReceiveListener?.onOTPReceived(it) }
                }
                CommonStatusCodes.TIMEOUT -> {
                    otpReceiveListener?.onOTPTimeOut()
                }
            }
        }
    }

    interface OTPReceiveListener {
        fun onOTPReceived(otp: String?)
        fun onOTPTimeOut()
    }
}

fun startSMSRetrieverClient(context: Context) {
    val client: SmsRetrieverClient = SmsRetriever.getClient(context)
    val task = client.startSmsRetriever()
    task.addOnSuccessListener { aVoid ->
        Log.e("Atiar OTP Receiver", "startSMSRetrieverClient addOnSuccessListener")
    }
    task.addOnFailureListener { e ->
        Log.e("Atiar OTP Receiver", "startSMSRetrieverClient addOnFailureListener" + e.stackTrace)
    }
}