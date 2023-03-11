package com.tinyDeveloper.na.ui.screens.main.useers

import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.hilt.navigation.compose.hiltViewModel
import com.tinyDeveloper.na.ui.component.DisplayAlertDialog
import com.tinyDeveloper.na.ui.screens.AppViewModel
import com.tinyDeveloper.na.utils.Constants.BASE_URL
import com.tinyDeveloper.na.utils.Resource

@Composable
fun UsersScreen(
    appViewModel: AppViewModel,
    usersViewModel: UsersViewModel = hiltViewModel(),
    navigateToUserScreen: (String) -> Unit,
    adminStatus: (Boolean) -> Unit,
    navigateToPersonalChat: () -> Unit
){
    adminStatus(appViewModel.roles.value?.addUser == "1")
    var isLoading by rememberSaveable{ mutableStateOf(false) }
    var errorIsOpen by rememberSaveable{ mutableStateOf(false) }
    val allUsersResponse = usersViewModel.allUsersResponse.value
    var hideIsOpen by remember{ mutableStateOf(false) }
    var deleteIsOpen by remember{ mutableStateOf(false) }

    var userPhone by remember{ mutableStateOf("") }

    LaunchedEffect(key1 = allUsersResponse){
        when(allUsersResponse){
            is Resource.Success -> {
                isLoading = false
                when(allUsersResponse.data?.message){
                    "Users Successfully got!" -> {}
                    else -> { errorIsOpen = true }
                }
            }
            is Resource.Error -> {
                errorIsOpen = true
                isLoading = false
            }
            is Resource.Loading -> {
                isLoading = true
            }
            else -> {
                if (allUsersResponse == null){
                    usersViewModel.getAll(
                        phone = appViewModel.phoneValue.value,
                        password = appViewModel.passwordValue.value
                    )
                }
            }
        }
    }

    val usersResponse = usersViewModel.usersResponse.value

    LaunchedEffect(key1 = usersResponse){
        when(usersResponse){
            is Resource.Success -> {
                when(usersResponse.data?.message){
                    "User Deleted SuccessFully!" -> {
                        usersViewModel.getAll(
                            phone = appViewModel.phoneValue.value,
                            password = appViewModel.passwordValue.value
                        )
                        usersViewModel.emptyUserResponse()
                    }
                    "User And Role Deleted SuccessFully!" -> {
                        usersViewModel.getAll(
                            phone = appViewModel.phoneValue.value,
                            password = appViewModel.passwordValue.value
                        )
                        usersViewModel.emptyUserResponse()
                        userPhone = ""
                    }
                    else -> { errorIsOpen = true; Log.i("delete user3", usersResponse.data?.message.toString()) }
                }
            }
            is Resource.Error -> { errorIsOpen = true; Log.i("delete user4", usersResponse.message.toString()) }
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
            if (
                (allUsersResponse is Resource.Success && allUsersResponse.data?.message != "Users Successfully got!") ||
                        allUsersResponse is Resource.Error
            ){
                usersViewModel.getAll(
                    phone = appViewModel.phoneValue.value,
                    password = appViewModel.passwordValue.value
                )
            }
            if (
                (usersResponse is Resource.Success && usersResponse.data?.message != "User And Role Deleted SuccessFully!") ||
                        usersResponse is Resource.Error
            ){
                if (userPhone.isNotEmpty()){
                    usersViewModel.delete(
                        phone = appViewModel.phoneValue.value,
                        password = appViewModel.passwordValue.value,
                        user = userPhone
                    )
                }
            }
            errorIsOpen = false
        },
        yesTitle = "تلاش مجدد",
        noTitle = "",
        dismiss = false
    )

    DisplayAlertDialog(
        openDialog = hideIsOpen,
        title = "انتقال به سطل زباله",
        description = "کاربر به سطل زباله انتقال یابد؟ کاربر به طور کامل پاک نمیشود.",
        onCloseClicked = {
            hideIsOpen = false
            userPhone = ""
        },
        onYesClicked = {
            hideIsOpen = false
            usersViewModel.delete(
                phone = appViewModel.phoneValue.value,
                password = appViewModel.passwordValue.value,
                user = userPhone
            )
        },
        yesTitle = "بله",
        noTitle = "خیر",
        dismiss = true
    )

    DisplayAlertDialog(
        openDialog = deleteIsOpen,
        title = "حذف کامل کاربر",
        description = "کاربر به طور کامل پاک شود؟ این کار قابل بازگشت نیست.",
        onCloseClicked = {
            deleteIsOpen = false
            userPhone = ""
        },
        onYesClicked = {
            deleteIsOpen = false
            usersViewModel.delete(
                phone = appViewModel.phoneValue.value,
                password = appViewModel.passwordValue.value,
                user = userPhone
            )
        },
        yesTitle = "بله",
        noTitle = "خیر",
        dismiss = true
    )

    UsersScreenContent(
        users = allUsersResponse?.data?.users,
        baseUrl = BASE_URL + appViewModel.baseInfoValue.value?.data?.baseInfo?.uploadUrl,
        isLoader = isLoading,
        deleteEnabled = appViewModel.roles.value?.deleteUser == "1",
        deleteClicked = {phone, status ->
            userPhone = phone
            if (status == "deleted"){
                deleteIsOpen = true
            }else {
                hideIsOpen = true
            }
        },
        navigateToUserScreen = {
            if (appViewModel.roles.value?.updateUsers == "1"){
                navigateToUserScreen(it)
            }else {
                navigateToPersonalChat()
            }
        },
        swipeToRefresh = {
            usersViewModel.getAll(
                phone = appViewModel.phoneValue.value,
                password = appViewModel.passwordValue.value
            )
        }
    )
}

