package com.tinyDeveloper.na.ui.screens.login.verification

import android.content.IntentFilter
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.tinyDeveloper.na.ui.component.DisplayAlertDialog
import com.tinyDeveloper.na.ui.component.InputChar
import com.tinyDeveloper.na.ui.component.NumberPad
import com.tinyDeveloper.na.ui.screens.AppViewModel
import com.tinyDeveloper.na.ui.theme.MEDIUM_PADDING
import com.tinyDeveloper.na.ui.theme.NUMBER_PAD_HEIGHT
import com.tinyDeveloper.na.ui.theme.PHONE_SCREEN_CONTENT_PADDING
import com.tinyDeveloper.na.ui.theme.SMALL_PADDING
import com.tinyDeveloper.na.utils.Constants.VERIFICATION_TIME
import com.tinyDeveloper.na.utils.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun VerificationScreen(
    navigateToMainScreen: () -> Unit,
    navigateToPhoneScreen: () -> Unit,
    appViewModel: AppViewModel
){
    val context = LocalContext.current
    LaunchedEffect(appViewModel.shouldStartSMSRetrieval.value){
        launch {
            if (appViewModel.shouldStartSMSRetrieval.value){
                Log.e("OTP", "SMS Retrieval is Starting...")
                /*val appSignatureHelper = AppSignatureHelper(context)
                Log.e("Atiar OTP Hashkey: ", "key: ${appSignatureHelper.appSignatures}")*/
                startSMSRetrieverClient(context)
            }
        }
    }

    LaunchedEffect(1){
        val myOTPReceiver = OTPReceiver()

        context.registerReceiver(myOTPReceiver, IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION))

        myOTPReceiver.init(object : OTPReceiver.OTPReceiveListener {
            override fun onOTPReceived(otp: String?) {
                Log.e("OTP ", "OTP Received  $otp")
                otp?.let { appViewModel.setVerificationCode(it) }
                appViewModel.checkCode()
                context.unregisterReceiver(myOTPReceiver)
                appViewModel.setShouldStartSMSRetrievalChange(false)
            }

            override fun onOTPTimeOut() {
                Log.e("OTP ", "Timeout")
            }
        })
    }

    var incorrectIsOpen by remember { mutableStateOf(false) }
    var errorIsOpen by remember { mutableStateOf(false) }
    DisplayAlertDialog(
        openDialog = errorIsOpen,
        title = "مشکل ارتباط با سرور",
        description = "متاسفانه نتوانستیم به سرور متصل شویم، لطفا اینترنت خود را برسی کنید.",
        onCloseClicked = { errorIsOpen = false },
        onYesClicked = {  appViewModel.checkCode(); errorIsOpen = false },
        yesTitle = "تلاش مجدد",
        noTitle = "انصراف"
    )
    DisplayAlertDialog(
        openDialog = incorrectIsOpen,
        title = "کد اشتباه است",
        description = "کد وارد شده با کد پیامک شده مطابقت ندارد لطفا پیام های خود را برسی کنید و کد ارسال شده را وارد کنید.",
        onCloseClicked = { },
        onYesClicked = { incorrectIsOpen = false },
        yesTitle = "متوجه شدم",
        noTitle = ""
    )
    val time = appViewModel.verificationTime.value
    LaunchedEffect(key1 = time){
        if(time > 0){
            delay(1000)
            appViewModel.onVerificationTimeChanged(time = time - 1)
        }
    }
    val checkCodeResponse = appViewModel.checkCodeValue.value?.data?.message?: ""
    LaunchedEffect(key1 = appViewModel.checkCodeValue.value){
        when(appViewModel.checkCodeValue.value){
            is Resource.Success -> {
                when(checkCodeResponse){
                    "Verification Code Is Correct!" -> { navigateToMainScreen(); appViewModel.setInfo() }
                    "Verification Code Is Incorrect!" -> { incorrectIsOpen = true }
                }
            }
            is Resource.Error -> { errorIsOpen = true }
            else -> {}
        }
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(MEDIUM_PADDING)
            .padding(bottom = PHONE_SCREEN_CONTENT_PADDING),
            horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.Center) {
            Text(text = "کد تایید به شماره موبایل  ${appViewModel.phoneValue.value} ارسال گردید")
            Spacer(modifier = Modifier.height(MEDIUM_PADDING))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                for(item in 5 downTo 0){
                    InputChar(
                        inputChar =
                        if (appViewModel.verificationCode.value.count() > item)
                            appViewModel.verificationCode.value[item].toString()
                        else "",
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            Spacer(modifier = Modifier.height(SMALL_PADDING))
            Row {
                OutlinedButton(
                    modifier = Modifier
                        .weight(0.5f)
                        .padding(end = SMALL_PADDING),
                    onClick = navigateToPhoneScreen) {
                    Text(text = "ویرایش شماره موبایل")
                }
                OutlinedButton(
                    modifier = Modifier
                        .weight(0.5f)
                        .padding(start = SMALL_PADDING),
                    enabled = time == 0,
                    onClick = {
                        appViewModel.onVerificationTimeChanged(VERIFICATION_TIME)
                        appViewModel.loginRequest()
                    }) {
                    Text(text =
                        if(time == 0) "ارسال مجدد"
                        else setTime(time)
                    )
                }
            }
            Spacer(modifier = Modifier.height(SMALL_PADDING))
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(if (appViewModel.checkCodeValue.value is Resource.Loading) 1f else 0f)
            )
            Spacer(modifier = Modifier.height(MEDIUM_PADDING))
            Text(modifier = Modifier.padding(MEDIUM_PADDING), text = "اگر از دو کاربر از پیش ساخته شده استفاده میکنید کدتایید 123456 را وارد کنید. این دو حساب برای تست برنامه ساخته شده اند.")
        }
        NumberPad(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .height(NUMBER_PAD_HEIGHT),
            phone = appViewModel.verificationCode.value,
            onPhoneChange = appViewModel::onVerificationCodeChange,
            acceptEnable = appViewModel.verificationCode.value.count() > 3 &&
                                appViewModel.checkCodeValue.value !is Resource.Loading,
            acceptClicked = appViewModel::checkCode
        )
    }
}

fun setTime(time: Int): String{
    return "${time / 60}:${time % 60} تا ارسال مجدد "
}