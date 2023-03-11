package com.tinyDeveloper.na.ui.screens.splash

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.tinyDeveloper.na.BuildConfig
import com.tinyDeveloper.na.R
import com.tinyDeveloper.na.domain.models.request.BasicRequest
import com.tinyDeveloper.na.ui.component.DisplayAlertDialog
import com.tinyDeveloper.na.ui.screens.AppViewModel
import com.tinyDeveloper.na.ui.theme.MEDIUM_PADDING
import com.tinyDeveloper.na.ui.theme.SMALL_PADDING
import com.tinyDeveloper.na.utils.Constants.BASES_SCREEN_TITLE
import com.tinyDeveloper.na.utils.Constants.BASE_SCREEN_TITLE
import com.tinyDeveloper.na.utils.Constants.CENTER_NAME
import com.tinyDeveloper.na.utils.Constants.MANAGER_NAME
import com.tinyDeveloper.na.utils.Constants.PART_NAME
import com.tinyDeveloper.na.utils.Constants.SPLASH_SCREEN_DELAY
import com.tinyDeveloper.na.utils.Resource
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navigateToMainScreen: () -> Unit,
    navigateToPhoneScreen: () -> Unit,
    appViewModel: AppViewModel,
    navigateToWelcomeScreen: (isMain: Boolean) -> Unit
){
    var stopTitle by remember{ mutableStateOf( "اطلاعات دریافت نشد") }
    var stopMessage by remember{ mutableStateOf( "سرور پاسخ درستی نداد دوباره تلاش کنید و درصورت تداوم مشکل با ابوالقاسمی تماس حاصل فرمایید.") }

    var updateTitle by remember{ mutableStateOf("بروزرسانی") }
    var updateText by remember{ mutableStateOf("نسخه جدیدی از این برنامه منتشر شده است ایا مایل به بروز رسانی هستید؟") }
 /*   val context = LocalContext.current
    val appSignatureHelper = AppSignatureHelper(context)
              Log.e("Atiar OTP Hashkey: ", "key: ${appSignatureHelper.appSignatures}")*/

    var baseInfoIsComplete by remember{ mutableStateOf(false) }
    var userRolesIsComplete by remember{ mutableStateOf(false) }
    val info by appViewModel.infoValue.collectAsState()
    var appIsNotEnabledIsOpen by remember{ mutableStateOf(false) }

    LaunchedEffect(key1 = true){
        delay(SPLASH_SCREEN_DELAY)
        appViewModel.getBaseInfo()
        appViewModel.getInfo()
    }

    val baseInfoState = appViewModel.baseInfoValue.value
    val userRoleState = appViewModel.userRoleValue.value
    val app = LocalContext.current as Activity
    var errorIsOpen by remember { mutableStateOf(false) }
    var newUpdateInOpen by remember { mutableStateOf(false) }
    var updateNeededIsOpen by remember{ mutableStateOf(false) }

    var ready by remember { mutableStateOf(false) }

    var isStarted by remember { mutableStateOf(false) }
    val offsetState by animateDpAsState(
        targetValue = if (isStarted) 0.dp else 100.dp,
        animationSpec = tween(durationMillis = 800)
    )
    val alphaState by animateFloatAsState(
        targetValue = if (isStarted) 1f else 0.2f,
        animationSpec = tween(durationMillis = 800)
    )
    val scaleState by animateFloatAsState(
        targetValue = if (isStarted) 260f else 120f,
        animationSpec = tween(durationMillis = 800)
    )
    LaunchedEffect(key1 = true) {
        isStarted = true
    }

    LaunchedEffect(key1 = baseInfoIsComplete, key2 = userRolesIsComplete, key3 = ready){
        if(baseInfoIsComplete && userRolesIsComplete && !updateNeededIsOpen && !newUpdateInOpen && !appIsNotEnabledIsOpen){
            navigateToNextScreen(
                info = info,
                navigateToPhoneScreen = navigateToPhoneScreen,
                navigateToMainScreen = navigateToMainScreen,
                navigateToWelcomeScreen = navigateToWelcomeScreen
            )
        }
    }

    LaunchedEffect(key1 = userRoleState){
        when(userRoleState){
            is Resource.Success ->  {
                when(userRoleState.data?.message){
                    "User Roles Successfully got!" -> {
                        userRolesIsComplete = true
                        if (userRoleState.data.roles?.editBaseInfo == "1" && userRoleState.data.roles.getBaseInfo == "1"){
                            appIsNotEnabledIsOpen = false
                        }
                    }
                    "This Admin Phone Number Not Founded!" -> { navigateToPhoneScreen() }
                    else -> {
                        errorIsOpen = true
                    }
                }
            }
            is Resource.Error -> {
                errorIsOpen = true
            }
            else -> {}
        }
    }


    DisplayAlertDialog(
        openDialog = errorIsOpen,
        title = "مشکل ارتباط با سرور",
        description = "متاسفانه نتوانستیم به سرور متصل شویم، لطفا اینترنت خود را برسی کنید.",
        onCloseClicked = {  app.finishAffinity();errorIsOpen = false },
        onYesClicked = { appViewModel.getBaseInfo(); errorIsOpen = false },
        yesTitle = "تلاش مجدد",
        noTitle = "انصراف",
        dismiss = false
    )

    DisplayAlertDialog(
        openDialog = appIsNotEnabledIsOpen,
        title = stopTitle,
        description = stopMessage,
        onCloseClicked = { },
        onYesClicked = { app.finishAffinity(); appIsNotEnabledIsOpen = false },
        yesTitle = "خروج",
        noTitle = ""
    )

    DisplayAlertDialog(
        openDialog = newUpdateInOpen,
        title = updateTitle,
        description = updateText,
        onCloseClicked = {
            ready = true
            newUpdateInOpen = false
        },
        onYesClicked = {
            val webIntent = Intent(Intent.ACTION_VIEW,
                Uri.parse(baseInfoState?.data?.baseInfo?.updateLink)
            )
            app.startActivity(webIntent)
            newUpdateInOpen = false
            app.finishAffinity();
        },
        yesTitle = "بله",
        noTitle = "فعلا نه!",
        dismiss = false
    )

    DisplayAlertDialog(
        openDialog = updateNeededIsOpen,
        title = "بروز رسانی",
        description = "این نسخه از برنامه بسیار قدیمی است لطفا نسخه جدید برنامه را دریافت کرده و ان را نصب کنید.",
        onCloseClicked = {
            if (userRoleState?.data?.roles?.editBaseInfo == "1" && userRoleState.data.roles.getBaseInfo == "1") {
                updateNeededIsOpen = false; ready = true
            }else app.finishAffinity(); updateNeededIsOpen = false
        },
        onYesClicked = {
            val webIntent = Intent(Intent.ACTION_VIEW,
                Uri.parse(baseInfoState?.data?.baseInfo?.updateLink)
            )
            app.startActivity(webIntent)
            app.finishAffinity()
            updateNeededIsOpen = false
        },
        yesTitle = "بروز رسانی",
        noTitle = "خروج",
        dismiss = false
    )

    LaunchedEffect(key1 = baseInfoState){
        when(baseInfoState){
            is Resource.Success -> {
                baseInfoIsComplete = true
                when(baseInfoState.data?.message?: ""){
                    "Base Info Successfully got!" -> {
                        stopTitle = baseInfoState.data?.baseInfo?.stopTitle?: stopTitle
                        stopMessage = baseInfoState.data?.baseInfo?.stopText?: stopMessage
                        updateTitle = baseInfoState.data?.baseInfo?.updateTitle?: updateTitle
                        updateText = baseInfoState.data?.baseInfo?.updateText?: updateText
                        appViewModel.setSnow(baseInfoState.data?.baseInfo?.isSnow == "1")
                        val manager = baseInfoState.data?.baseInfo?.manager?: MANAGER_NAME
                        val part = baseInfoState.data?.baseInfo?.part?: PART_NAME
                        val center = baseInfoState.data?.baseInfo?.center?: CENTER_NAME
                        if (manager.isNotEmpty())
                            MANAGER_NAME = manager
                        if(part.isNotEmpty()){
                            PART_NAME = part
                            BASES_SCREEN_TITLE = "$part ها"
                            BASE_SCREEN_TITLE = part
                        }
                        if (center.isNotEmpty())
                            CENTER_NAME = center
                        val appVersion = BuildConfig.VERSION_CODE
                        if (baseInfoState.data?.baseInfo?.appEnabled == "1"){
                            if(baseInfoState.data.baseInfo.minVersion.toInt() <= appVersion &&
                                    appVersion < baseInfoState.data.baseInfo.lastVersion.toInt()){
                                newUpdateInOpen = true
                            } else if (appVersion < baseInfoState.data.baseInfo.minVersion.toInt()){
                                updateNeededIsOpen = true
                            } else {
                                ready = true
                            }
                        } else {
                            appIsNotEnabledIsOpen = true
                        }
                        if(appViewModel.infoValue.value?.phone != "" && appViewModel.infoValue.value?.password != ""){
                            appViewModel.getUserRole()
                        } else{
                            userRolesIsComplete = true
                        }
                    }
                    "Active Base Info Not Founded!" -> {
                        appIsNotEnabledIsOpen = true
                    }
                    else -> { errorIsOpen = true }
                }
            }
            is Resource.Error -> errorIsOpen = true
            else -> {}
        }
    }

    Box(modifier = Modifier.fillMaxSize()){
        Image(modifier = Modifier
            .align(Alignment.Center)
            .alpha(alpha = alphaState)
            .offset(y = offsetState)
            .padding(bottom = 180.dp)
            .size(scaleState.dp),
            painter = painterResource(id = R.drawable.app_icon), contentDescription = "app icon")
        Column(modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomCenter)
            .padding(bottom = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                if(baseInfoIsComplete)
                    Image(modifier = Modifier.size(24.dp),painter = painterResource(id = R.drawable.green_checked), contentDescription = "user role checked")
                else
                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                Spacer(modifier = Modifier.width(MEDIUM_PADDING))
                Text(text = if (baseInfoIsComplete) "اطلاعات برنامه دریافت شد" else "در حال دریافت اطلاعات برنامه",
                    fontSize = MaterialTheme.typography.titleSmall.fontSize)
            }
            Spacer(modifier = Modifier.height(SMALL_PADDING))
            Row {
                if(userRolesIsComplete)
                    Image(modifier = Modifier.size(24.dp),painter = painterResource(id = R.drawable.green_checked), contentDescription = "user role checked")
                else
                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                Spacer(modifier = Modifier.width(MEDIUM_PADDING))
                Text(text = if (userRolesIsComplete) "اطلاعات کاربر دریافت شد" else "در حال دریافت اطلاعات کاربر",
                    fontSize = MaterialTheme.typography.titleSmall.fontSize
                )
            }
            Spacer(modifier = Modifier.height(64.dp))
            Text(text = "سامانه یکپارچه ارنا")
        }
    }

}

fun navigateToNextScreen(
    info: BasicRequest?,
    navigateToMainScreen: () -> Unit,
    navigateToPhoneScreen: () -> Unit,
    navigateToWelcomeScreen: (isMain: Boolean) -> Unit
){
    if(info?.phone != "" && info?.password != ""){
        navigateToMainScreen()
    }else{
        navigateToWelcomeScreen(false)
    }
}