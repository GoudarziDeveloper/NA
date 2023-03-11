package com.tinyDeveloper.na.ui.screens.main.settings

import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.*
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.tinyDeveloper.na.ui.component.DisplayAlertDialog
import com.tinyDeveloper.na.ui.screens.AppViewModel
import com.tinyDeveloper.na.utils.Resource

@Composable
fun SettingsScreen(appViewModel: AppViewModel, settingsViewModel: SettingsViewModel){
    var isLoading by remember{ mutableStateOf(false) }
    var errorIsOpen by remember{ mutableStateOf(false) }

    val getBaseInfoResponse = settingsViewModel.getBaseInfoResponse.value
    LaunchedEffect(key1 = getBaseInfoResponse){
        when(getBaseInfoResponse){
            is Resource.Success -> {
                isLoading = false
                when(getBaseInfoResponse.data?.message){
                    "Base Info Successfully got!" -> {
                        settingsViewModel.updateFields(
                            appEnabled = getBaseInfoResponse.data.baseInfo.appEnabled,
                            checkCodeUrl = getBaseInfoResponse.data.baseInfo.checkCodeUrl,
                            id = getBaseInfoResponse.data.baseInfo.id,
                            lastVersion = getBaseInfoResponse.data.baseInfo.lastVersion,
                            messageText = getBaseInfoResponse.data.baseInfo.messageText,
                            messageTitle = getBaseInfoResponse.data.baseInfo.messageTitle,
                            messageEnabled = getBaseInfoResponse.data.baseInfo.messageEnabled,
                            minVersion = getBaseInfoResponse.data.baseInfo.minVersion,
                            sendCodeUrl = getBaseInfoResponse.data.baseInfo.sendCodeUrl,
                            status = getBaseInfoResponse.data.baseInfo.status,
                            stopText = getBaseInfoResponse.data.baseInfo.stopText,
                            stopTitle = getBaseInfoResponse.data.baseInfo.stopTitle,
                            updateLink = getBaseInfoResponse.data.baseInfo.updateLink,
                            updateText = getBaseInfoResponse.data.baseInfo.updateText,
                            updateTitle = getBaseInfoResponse.data.baseInfo.updateTitle,
                            uploadUrl = getBaseInfoResponse.data.baseInfo.uploadUrl,
                            isSnow = getBaseInfoResponse.data.baseInfo.isSnow,
                            manager = getBaseInfoResponse.data.baseInfo.manager,
                            part = getBaseInfoResponse.data.baseInfo.part,
                            center = getBaseInfoResponse.data.baseInfo.center,
                        )
                        appViewModel.setBaseInfo(baseInfo = getBaseInfoResponse.data.baseInfo)
                    }
                    else -> { errorIsOpen = true }
                }
            }
            is Resource.Error -> {
                errorIsOpen = true; isLoading = false
            }
            is Resource.Loading -> { isLoading = true }
            else -> {
                settingsViewModel.updateFields(phone = appViewModel.phoneValue.value, password = appViewModel.passwordValue.value)
                settingsViewModel.getBaseInfo()
            }
        }
    }

    val basicResponse = settingsViewModel.basicResponse.value
    LaunchedEffect(key1 = basicResponse){
        when(basicResponse){
            is Resource.Success -> {
                isLoading = false
                when(basicResponse.data?.message){
                    "Base Info Updated Successfully!" -> {}
                    else -> { errorIsOpen = true }
                }
            }
            is Resource.Error -> { errorIsOpen = true; isLoading = false }
            is Resource.Loading -> { isLoading = true }
            else -> {}
        }
    }

    DisplayAlertDialog(
        openDialog = errorIsOpen,
        title = "مشکل ارتباط با سرور",
        description = "متاسفانه نتوانستیم به سرور متصل شویم، لطفا اینترنت خود را برسی کنید.",
        onCloseClicked = { },
        onYesClicked = {
            if (getBaseInfoResponse != null && getBaseInfoResponse.data?.message != "Base Info Successfully got!"){
                settingsViewModel.getBaseInfo()
            }
            if (basicResponse != null && basicResponse.data?.message != "Base Info Updated Successfully!"){
                settingsViewModel.updateBaseInfo()
            }
            errorIsOpen = false
        },
        yesTitle = "تلاش مجدد",
        noTitle = "",
        dismiss = false
    )
    val scrollState = rememberScrollState()
    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = isLoading),
        onRefresh = { settingsViewModel.getBaseInfo() }
    ) {
        if (!isLoading && getBaseInfoResponse is Resource.Success){
            SettingsScreenContent(settingsViewModel = settingsViewModel, scrollState = scrollState)
        }else {
            SettingsScreenShimmer(scrollState = scrollState)
        }
    }
}