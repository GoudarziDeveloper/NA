package com.tinyDeveloper.na.ui.screens.main.bases

import androidx.compose.runtime.*
import com.tinyDeveloper.na.ui.component.DisplayAlertDialog
import com.tinyDeveloper.na.ui.screens.AppViewModel
import com.tinyDeveloper.na.utils.Constants.CENTER_NAME
import com.tinyDeveloper.na.utils.Resource

@Composable
fun BasesScreen(
    appViewModel: AppViewModel,
    adminStatus: (Boolean) -> Unit,
    basesViewModel: BasesViewModel,
    navigateToBaseScreen: (String) -> Unit
){
    adminStatus(appViewModel.roles.value?.addBase == "1")

    val getAllBasesResponse = basesViewModel.getAllBasesResponse.value
    var isLoading by remember{ mutableStateOf(false) }
    var errorIsOpen by remember { mutableStateOf(false) }
    var deleteIsOpen by remember{ mutableStateOf(false) }
    var messageIsOpen by remember{ mutableStateOf(false) }
    var deleteId by remember{ mutableStateOf("") }

    LaunchedEffect(key1 = getAllBasesResponse){
        when(getAllBasesResponse){
            is Resource.Success -> {
                isLoading = false
                when(getAllBasesResponse.data?.message){
                    "Bases Got Successfully!" -> { }
                    else -> {
                        errorIsOpen = true
                    }
                }
            }
            is Resource.Error -> { isLoading = false; errorIsOpen = true }
            is Resource.Loading -> { isLoading = true }
            else -> {
                basesViewModel.getAll(
                    phone = appViewModel.phoneValue.value,
                    password = appViewModel.passwordValue.value
                )
            }
        }
    }

    val basicResponse = basesViewModel.basicResponse.value
    LaunchedEffect(key1 = basicResponse){
        when(basicResponse){
            is Resource.Success -> {
                isLoading = false
                when(basicResponse.data?.message){
                    "Base Deleted Successfully!" -> {
                        basesViewModel.getAll(phone = appViewModel.phoneValue.value, password = appViewModel.passwordValue.value)
                        basesViewModel.emptyBasicResponse()
                    }
                    "You Cant Delete Center!" -> {
                        messageIsOpen = true 
                        basesViewModel.emptyBasicResponse()
                    }
                    else -> { errorIsOpen = true }
                }
            }
            is Resource.Error -> { isLoading = false; errorIsOpen = true }
            is Resource.Loading -> { isLoading = true }
            else -> { }
        }
    }

    DisplayAlertDialog(
        openDialog = errorIsOpen,
        title = "مشکل ارتباط با سرور",
        description = "متاسفانه نتوانستیم به سرور متصل شویم، لطفا اینترنت خود را برسی کنید.",
        onCloseClicked = { },
        onYesClicked = {
            if (getAllBasesResponse != null && getAllBasesResponse.data?.message != "Bases Got Successfully!"){
                basesViewModel.getAll(phone = appViewModel.phoneValue.value, password = appViewModel.passwordValue.value)
            }
            if (basicResponse != null && basicResponse.data?.message != "Base Deleted Successfully!"){
                basesViewModel.delete(
                    phone = appViewModel.phoneValue.value,
                    password = appViewModel.passwordValue.value,
                    id = deleteId
                )
            }
            errorIsOpen = false
        },
        yesTitle = "تلاش مجدد",
        noTitle = "",
        dismiss = false
    )

    DisplayAlertDialog(
        openDialog = deleteIsOpen,
        title = "حذف کامل پایگاه",
        description = "پایگاه به طور کامل پاک شود؟ این کار قابل بازگشت نیست.",
        onCloseClicked = {
            deleteIsOpen = false
            deleteId = ""
        },
        onYesClicked = {
            deleteIsOpen = false
            basesViewModel.delete(
                phone = appViewModel.phoneValue.value,
                password = appViewModel.passwordValue.value,
                id = deleteId
            )
        },
        yesTitle = "بله",
        noTitle = "خیر",
        dismiss = true
    )

    DisplayAlertDialog(
        openDialog = messageIsOpen,
        title = "خطا در حذف",
        description = "نمیتوان $CENTER_NAME را پاک کرد!",
        onCloseClicked = {
            messageIsOpen = true

        },
        onYesClicked = {
            messageIsOpen = false
        },
        yesTitle = "باشه",
        noTitle = "",
        dismiss = true
    )

    BasesScreenContent(
        isLoading = isLoading || getAllBasesResponse !is Resource.Success,
        bases = getAllBasesResponse?.data?.bases ?: emptyList(),
        onItemClicked =  navigateToBaseScreen,
        onDeleteClicked = { deleteId = it; deleteIsOpen = true },
        swipeToRefresh = {
            basesViewModel.getAll(phone = appViewModel.phoneValue.value, password = appViewModel.passwordValue.value)
        }
    )
}