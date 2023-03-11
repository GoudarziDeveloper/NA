package com.tinyDeveloper.na.ui.screens.main.jobs.job.submissions

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.hilt.navigation.compose.hiltViewModel
import com.tinyDeveloper.na.ui.component.DisplayAlertDialog
import com.tinyDeveloper.na.ui.screens.AppViewModel
import com.tinyDeveloper.na.utils.Constants.BASE_URL
import com.tinyDeveloper.na.utils.Constants.UPLOAD_URL
import com.tinyDeveloper.na.utils.Resource

@Composable
fun SubmissionsScreen(
    appViewModel: AppViewModel,
    id: String,
    submissionsViewModel: SubmissionsViewModel,
    navigateToSubmissionScreen: (jobId: String, jobStatus:String) -> Unit
){
    var isLoading by rememberSaveable{ mutableStateOf(false) }
    var errorIsOpen by rememberSaveable{ mutableStateOf(false) }
    val getAllResponse = submissionsViewModel.getAllResponse.value
    LaunchedEffect(key1 = getAllResponse){
        when(getAllResponse){
            is Resource.Success -> {
                isLoading = false
                when(getAllResponse.data?.message){
                    "Submissions Got Successfully!" -> {}
                    else -> { errorIsOpen = true }
                }
            }
            is Resource.Error -> { isLoading = false; errorIsOpen = true }
            is Resource.Loading -> { isLoading = true }
            else -> {
                submissionsViewModel.getAll(
                    phone = appViewModel.phoneValue.value,
                    password = appViewModel.passwordValue.value,
                    id = id
                )
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
            submissionsViewModel.getAll(
                phone = appViewModel.phoneValue.value,
                password = appViewModel.passwordValue.value,
                id = id
            )
        },
        yesTitle = "تلاش مجدد",
        noTitle = "",
        dismiss = false
    )

    SubmissionsScreenContent(
        submissions = if (!errorIsOpen) getAllResponse?.data?.submissions?: emptyList() else null,
        isLoading = isLoading,
        baseUrl = BASE_URL + (appViewModel.baseInfoValue.value?.data?.baseInfo?.uploadUrl?: UPLOAD_URL),
        navigateToSubmissionScreen = navigateToSubmissionScreen,
        swipeToRefresh = {
            submissionsViewModel.getAll(
                phone = appViewModel.phoneValue.value,
                password = appViewModel.passwordValue.value,
                id = id
            )
        }
    )
}