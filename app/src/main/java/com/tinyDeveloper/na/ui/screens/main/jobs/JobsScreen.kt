package com.tinyDeveloper.na.ui.screens.main.jobs

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.hilt.navigation.compose.hiltViewModel
import com.tinyDeveloper.na.ui.component.DisplayAlertDialog
import com.tinyDeveloper.na.ui.screens.AppViewModel
import com.tinyDeveloper.na.utils.Resource
import com.tinyDeveloper.na.utils.StateValues

@Composable
fun JobsScreen(
    appViewModel: AppViewModel,
    adminStatus: (Boolean) -> Unit,
    jobsViewModel: JobsViewModel = hiltViewModel(),
    navigateToJobScreen: (String) -> Unit,
    navigateToJobDetailsScreen: (String) -> Unit
){
    adminStatus(appViewModel.roles.value?.addJob == "1")
    val getAllResponse = jobsViewModel.getAllResponse.value
    var isLoading by rememberSaveable{ mutableStateOf(false) }
    var errorIsOpen by rememberSaveable{ mutableStateOf(false) }
    var hideIsOpen by remember{ mutableStateOf(false) }
    var deleteIsOpen by remember{ mutableStateOf(false) }
    var deleteId by remember{ mutableStateOf("-1") }
    LaunchedEffect(key1 = getAllResponse){
        when(getAllResponse){
            is Resource.Success -> {
                isLoading = false
                when(getAllResponse.data?.message){
                    "Jobs Got Successfully!" -> {}
                    "This User Has No Any Not Worked Job!" -> {}
                    "This User Has No Any Worked Job!" -> {}
                    "Where Is No Any Job!" -> {}
                    "This User Has No Any Job!" -> {}
                    else -> {
                        errorIsOpen = true
                    }
                }
            }
            is Resource.Error -> {
                isLoading = false
                errorIsOpen = true
            }
            is Resource.Loading -> { isLoading = true }
            else -> {
                if(appViewModel.roles.value?.getSubmissions == "1"){
                    jobsViewModel.getAll(
                        phone = appViewModel.phoneValue.value,
                        password = appViewModel.passwordValue.value
                    )
                }else {
                    jobsViewModel.getAllUserJobs(
                        phone = appViewModel.phoneValue.value,
                        password = appViewModel.passwordValue.value
                    )
                }
            }
        }
    }

    val deleteResponse = jobsViewModel.basicResponse.value
    LaunchedEffect(key1 = deleteResponse){
        when(deleteResponse){
            is Resource.Success -> {
                isLoading = false
                when(deleteResponse.data?.message){
                    "Job Deleted Successfully!" -> {
                        jobsViewModel.getAll(
                            phone = appViewModel.phoneValue.value,
                            password = appViewModel.passwordValue.value
                        )
                        jobsViewModel.emptyBasicResponse()
                        deleteId = ""
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
            if ((getAllResponse is Resource.Success && getAllResponse.data?.message != "Jobs Got Successfully!") ||
                    getAllResponse is Resource.Error){
                if(appViewModel.roles.value?.getSubmissions == "1"){
                    jobsViewModel.getAll(
                        phone = appViewModel.phoneValue.value,
                        password = appViewModel.passwordValue.value
                    )
                }else {
                    jobsViewModel.getAllUserJobs(
                        phone = appViewModel.phoneValue.value,
                        password = appViewModel.passwordValue.value
                    )
                }
            }
            if ((deleteResponse is Resource.Success && deleteResponse.data?.message != "Job Deleted Successfully!") ||
                    deleteResponse is Resource.Error){
                jobsViewModel.delete(
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
        openDialog = hideIsOpen,
        title = "انتقال به سطل زباله",
        description = "کارخواسته به سطل زباله انتقال یابد؟ کارخواسته به طور کامل پاک نمیشود.",
        onCloseClicked = {
            hideIsOpen = false
            deleteId = ""
        },
        onYesClicked = {
            hideIsOpen = false
            jobsViewModel.delete(
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
        openDialog = deleteIsOpen,
        title = "حذف کامل کارخواسته",
        description = "کارخواسته به طور کامل پاک شود؟ این کار قابل بازگشت نیست.",
        onCloseClicked = {
            deleteIsOpen = false
            deleteId = ""
        },
        onYesClicked = {
            deleteIsOpen = false
            jobsViewModel.delete(
                phone = appViewModel.phoneValue.value,
                password = appViewModel.passwordValue.value,
                id = deleteId
            )
        },
        yesTitle = "بله",
        noTitle = "خیر",
        dismiss = true
    )

    JobsScreenContent(
        jobs = getAllResponse?.data?.jobs,
        isLoading = isLoading,
        isAdmin = appViewModel.roles.value?.getSubmissions == "1",
        isDelete = appViewModel.roles.value?.deleteJob == "1",
        isUpdate = appViewModel.roles.value?.updateJob == "1",
        onUpdateClicked = navigateToJobScreen,
        onDeleteClicked = {
             deleteId = it
             if (jobsViewModel.findStatus(it) != StateValues.DELETED.value) hideIsOpen = true
             else deleteIsOpen = true
        },
        itemClicked = { navigateToJobDetailsScreen(it); if(jobsViewModel.jobId.value != it) jobsViewModel.emptyGetResponse() },
        swipeToRefresh = {
            if(appViewModel.roles.value?.getSubmissions == "1"){
                jobsViewModel.getAll(
                    phone = appViewModel.phoneValue.value,
                    password = appViewModel.passwordValue.value
                )
            }else {
                jobsViewModel.getAllUserJobs(
                    phone = appViewModel.phoneValue.value,
                    password = appViewModel.passwordValue.value
                )
            }
        },
    )
}