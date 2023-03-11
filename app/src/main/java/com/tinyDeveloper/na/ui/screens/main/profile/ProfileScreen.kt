package com.tinyDeveloper.na.ui.screens.main.profile

import android.app.Activity
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.tinyDeveloper.na.ui.component.DisplayAlertDialog
import com.tinyDeveloper.na.ui.screens.AppViewModel
import com.tinyDeveloper.na.ui.screens.main.useers.UsersViewModel
import com.tinyDeveloper.na.utils.Constants.BASE_URL
import com.tinyDeveloper.na.utils.Resource
import com.tinyDeveloper.na.utils.bitmapToBase64

@Composable
fun ProfileScreen(
    appViewModel: AppViewModel,
    profileViewModel: ProfileViewModel = hiltViewModel(),
    usersViewModel: UsersViewModel
){
    var isLoading by remember{ mutableStateOf(false) }
    var errorIsOpen by remember { mutableStateOf(false) }
    var exitIsOpen by remember { mutableStateOf(false) }
    val app = LocalContext.current as Activity

    val getResponse = profileViewModel.getResponse.value
    LaunchedEffect(key1 = getResponse){
        when(getResponse){
            is Resource.Success -> {
                isLoading = false
                if (profileViewModel.image.value != null) profileViewModel.updateImage(null)
                when(getResponse.data?.message){
                    "User Successfully got!" -> {}
                    else -> { errorIsOpen = true }
                }
            }
            is Resource.Error -> { isLoading = false; errorIsOpen = true }
            is Resource.Loading -> { isLoading = true }
            else -> {
                profileViewModel.get(
                    phone = appViewModel.phoneValue.value, password = appViewModel.passwordValue.value
                )
            }
        }
    }

    val updateResponse = profileViewModel.updateResponse.value
    LaunchedEffect(key1 = updateResponse){
        when(updateResponse){
            is Resource.Success -> {
                isLoading = false
                when(updateResponse.data?.message){
                    "User Updated SuccessFully!" -> {
                        usersViewModel.getAll(
                            phone = appViewModel.phoneValue.value,
                            password = appViewModel.passwordValue.value
                        )
                        appViewModel.changeProfileImage(updateResponse.data.image)
                    }
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
            profileViewModel.get(
                phone = appViewModel.phoneValue.value, password = appViewModel.passwordValue.value
            )
            errorIsOpen = false
        },
        yesTitle = "تلاش مجدد",
        noTitle = "",
        dismiss = false
    )

    DisplayAlertDialog(
        openDialog = exitIsOpen,
        title = "خروج از حساب",
        description = "با این کار از حساب خود (${appViewModel.phoneValue.value}) خارج میشوید. ایا مایل به ادامه دادن هستید؟\nبرنامه به صورت خودکار بسته میشود و شما باید دوباره برنامه را باز کرده و وارد حساب خود شوید.",
        onCloseClicked = { exitIsOpen = false },
        onYesClicked = {
            exitIsOpen = false
            appViewModel.emptyInfo()
            app.finishAffinity()
        },
        yesTitle = "بله",
        noTitle = "خیر",
        dismiss = true
    )

    SetProfileScreenContent(
        image = profileViewModel.image.value,
        isLoading = isLoading,
        editMode = false,
        user = getResponse?.data?.user,
        base = getResponse?.data?.base,
        baseUrl = "$BASE_URL${appViewModel.baseInfoValue.value?.data?.baseInfo?.uploadUrl?:"/uploads/"}",
        updateImage = {
            profileViewModel.updateImage(it)
        },
        swipeToRefresh = {
            profileViewModel.get(
                phone = appViewModel.phoneValue.value, password = appViewModel.passwordValue.value
            )
        },
        exitClicked = {
            exitIsOpen = true
        }
    ) {
        profileViewModel.update(
            phone = appViewModel.phoneValue.value,
            password = appViewModel.passwordValue.value,
            image =
            if (profileViewModel.image.value == null) "-1"
            else profileViewModel.image.value!!.bitmapToBase64()
        )
    }
}