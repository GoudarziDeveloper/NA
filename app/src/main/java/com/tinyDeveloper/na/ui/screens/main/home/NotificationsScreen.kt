package com.tinyDeveloper.na.ui.screens.main.home

import android.util.Log
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.tinyDeveloper.na.ui.component.DisplayAlertDialog
import com.tinyDeveloper.na.ui.screens.AppViewModel
import com.tinyDeveloper.na.ui.screens.main.home.chat.ChatViewModel
import com.tinyDeveloper.na.utils.Resource
import io.getstream.chat.android.compose.viewmodel.messages.AttachmentsPickerViewModel
import io.getstream.chat.android.compose.viewmodel.messages.MessageComposerViewModel
import io.getstream.chat.android.compose.viewmodel.messages.MessageListViewModel

@Composable
fun NotificationsScreen(
    appViewModel: AppViewModel,
    notificationsViewModel: NotificationsViewModel = hiltViewModel(),
    navigateToNotificationScreen: (id: Int) -> Unit,
    navigateToUpdate: (String) -> Unit,
    adminStatus:(Boolean) -> Unit,
    chatViewModel: ChatViewModel,
){
    val isAdmin =
        appViewModel.roles.value != null &&
        appViewModel.roles.value!!.getNotifications == "1" && (
                        appViewModel.roles.value!!.updateNotification == "1" ||
                        appViewModel.roles.value!!.deleteNotification == "1" ||
                        appViewModel.roles.value!!.addNotification == "1")


    val phone = appViewModel.phoneValue.value
    val password = appViewModel.passwordValue.value
    val id = remember{ mutableStateOf("-1") }
    var isLoading by remember { mutableStateOf(false) }

    val notificationResponse = notificationsViewModel.notificationResponse.value
    var errorIsOpen by remember{ mutableStateOf(false) }
    var messageIsOpened by remember{ mutableStateOf(false) }
    var hideIsOpen by remember{ mutableStateOf(false) }
    var deleteIsOpen by remember{ mutableStateOf(false) }
    LaunchedEffect(key1 = notificationResponse){
        when(notificationResponse){
            is Resource.Success -> {
                isLoading = false
                when(notificationResponse.data?.message){
                    "Notification Deleted Successfully!" -> {
                        if (id.value != "-1"){
                            if (isAdmin && phone.isNotEmpty() && password.isNotEmpty()){
                                notificationsViewModel.getAllCommonNotifications(phone = phone, password = password)
                                notificationsViewModel.getAllPrivateNotifications(phone = phone, password = password)
                            }else {
                                notificationsViewModel.getCommonNotifications(phone = phone, password = password)
                                notificationsViewModel.getPrivateNotifications(phone = phone, password = password)
                            }
                            notificationsViewModel.emptyNotificationResponse()
                            id.value = "-1"
                        }
                    }
                    else -> { messageIsOpened = true }
                }
            }
            is Resource.Error -> {
                isLoading = false
                errorIsOpen = true
            }
            is Resource.Loading -> { isLoading = true }
            else -> {  }
        }
    }

    val commonNotificationsResponse = notificationsViewModel.commonNotificationsResponse.value
    val personalNotificationsResponse = notificationsViewModel.privateNotificationsResponse.value

    LaunchedEffect(
        key1 = commonNotificationsResponse
    ){
        when(commonNotificationsResponse){
            is Resource.Success -> {
                isLoading = false
                when(commonNotificationsResponse.data?.message){
                    "Notifications Got Successfully!" -> {}
                    "Not Found Any Notification!" -> {}
                    else -> { errorIsOpen = true }
                }
            }
            is Resource.Error -> { errorIsOpen = true; isLoading = false; Log.i("open", commonNotificationsResponse.message.toString()) }
            is Resource.Loading -> { isLoading = true }
            else -> {
                if (isAdmin){
                    notificationsViewModel.getAllCommonNotifications(phone = phone, password = password)
                }else {
                    notificationsViewModel.getCommonNotifications(phone = phone, password = password)
                }
            }
        }
    }

    LaunchedEffect(
        key1 = personalNotificationsResponse
    ){
        when(personalNotificationsResponse){
            is Resource.Success -> {
                isLoading = false
                when(personalNotificationsResponse.data?.message){
                    "Notifications Got Successfully!" -> {}
                    "Not Found Any Notification!" -> {}
                    else -> { errorIsOpen = true }
                }
            }
            is Resource.Error -> { errorIsOpen = true; isLoading = false }
            is Resource.Loading -> { isLoading = true }
            else -> {
                if (isAdmin){
                    notificationsViewModel.getAllPrivateNotifications(phone = phone, password = password)
                }else {
                    notificationsViewModel.getPrivateNotifications(phone = phone, password = password)
                }
            }
        }
    }

    DisplayAlertDialog(
        openDialog = errorIsOpen,
        title = "مشکل ارتباط با سرور",
        description = "متاسفانه نتوانستیم به سرور متصل شویم، لطفا اینترنت خود را برسی کنید.",
        onCloseClicked = { },
        onYesClicked = {
            errorIsOpen = false
            if (
                (notificationResponse is Resource.Success &&
                notificationResponse.data?.message != "Notification Deleted Successfully!") ||
                notificationResponse is Resource.Error
            ){
                notificationsViewModel.findNotification(id.value)?.let { notification ->
                    if (notification.status == "deleted"){
                        deleteIsOpen = true
                    }else {
                        hideIsOpen = true
                    }
                }
            }
            if (
                (commonNotificationsResponse is Resource.Success &&
                commonNotificationsResponse.data?.message != "Notifications Got Successfully!") ||
                commonNotificationsResponse is Resource.Error
            ){

                if (isAdmin){
                    notificationsViewModel.getAllCommonNotifications(phone = phone, password = password)
                    notificationsViewModel.getAllPrivateNotifications(phone = phone, password = password)
                }else {
                    notificationsViewModel.getCommonNotifications(phone = phone, password = password)
                    notificationsViewModel.getPrivateNotifications(phone = phone, password = password)
                }
            }
        },
        yesTitle = "تلاش مجدد",
        noTitle = "",
        dismiss = false
    )

    DisplayAlertDialog(
        openDialog = messageIsOpened,
        title = "سطح دسترسی",
        description = "سرور دسترسی شما را تایید نکرد ممکن است دسترسی شما محدود شده باشد، میتوانید مجدد تلاش کنید",
        onCloseClicked = { },
        onYesClicked = { messageIsOpened = false },
        yesTitle = "باشه",
        noTitle = "",
        dismiss = false
    )

    DisplayAlertDialog(
        openDialog = hideIsOpen,
        title = "انتقال به سطل زباله",
        description = "اعلان به سطل زباله انتقال یابد؟ اعلان به طور کامل پاک نمیشود.",
        onCloseClicked = { hideIsOpen = false },
        onYesClicked = {
            hideIsOpen = false
            notificationsViewModel.deleteNotification(phone = phone, password = password, id = id.value)
        },
        yesTitle = "بله",
        noTitle = "خیر",
        dismiss = true
    )

    DisplayAlertDialog(
        openDialog = deleteIsOpen,
        title = "حذف کامل اعلان",
        description = "اعلان به طور کامل پاک شود؟ این کار قابل بازگشت نیست.",
        onCloseClicked = { deleteIsOpen = false },
        onYesClicked = {
            deleteIsOpen = false
            notificationsViewModel.deleteNotification(phone = phone, password = password, id = id.value)
        },
        yesTitle = "بله",
        noTitle = "خیر",
        dismiss = true
    )

    val commonNotification =
        notificationsViewModel.commonNotificationsResponse.value
    val privateNotification =
        notificationsViewModel.privateNotificationsResponse.value

    TabLayout(
        appViewModel = appViewModel,
        onItemClicked = navigateToNotificationScreen,
        commonNotification = commonNotification?.data?.notifications,
        privateNotification = privateNotification?.data?.notifications,
        isLoading = isLoading,
        updateClicked = navigateToUpdate ,
        deleteClicked = {
            id.value = it
            notificationsViewModel.findNotification(it)?.let { notification ->
                if (notification.status == "deleted"){
                    deleteIsOpen = true
                }else {
                    hideIsOpen = true
                }
            }
        },
        swipeToRefreshCommon = {
            if(isAdmin){
                notificationsViewModel.getAllCommonNotifications(phone = appViewModel.phoneValue.value, password = appViewModel.passwordValue.value)
            }else{
                notificationsViewModel.getCommonNotifications(phone = appViewModel.phoneValue.value, password = appViewModel.passwordValue.value)
            }
        },
        swipeToRefreshPersonal = {
            if(isAdmin){
                notificationsViewModel.getAllPrivateNotifications(phone = appViewModel.phoneValue.value, password = appViewModel.passwordValue.value)
            }else{
                notificationsViewModel.getPrivateNotifications(phone = appViewModel.phoneValue.value, password = appViewModel.passwordValue.value)
            }
        },
        chatViewModel = chatViewModel,
        adminStatus = adminStatus,
        currentPage = notificationsViewModel.currentPage.value,
        setCurrentPage = notificationsViewModel::setCurrentPage,
        showChat = appViewModel.chatInfo.value != null
    )
}