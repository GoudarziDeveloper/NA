package com.tinyDeveloper.na.ui.screens.main.jobs.job

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.tinyDeveloper.na.ui.component.DisplayAlertDialog
import com.tinyDeveloper.na.ui.screens.AppViewModel
import com.tinyDeveloper.na.ui.screens.main.jobs.JobsViewModel
import com.tinyDeveloper.na.ui.screens.main.jobs.job.submissions.JobDetailsScreenContent
import com.tinyDeveloper.na.ui.screens.main.jobs.job.submissions.SubmissionsViewModel
import com.tinyDeveloper.na.utils.Constants.BASE_URL
import com.tinyDeveloper.na.utils.Constants.UPLOAD_URL
import com.tinyDeveloper.na.utils.Resource
import kotlinx.coroutines.delay

@Composable
fun JobDetailScreen(
    appViewModel: AppViewModel,
    jobsViewModel: JobsViewModel = hiltViewModel(),
    submissionsViewModel: SubmissionsViewModel,
    id: String,
    jobDetail: (Boolean) -> Unit,
    setFabNavigate: (jobId: String, JobStatus: String) -> Unit
){
    var isLoading by rememberSaveable{ mutableStateOf(false) }
    var errorIsOpen by rememberSaveable{ mutableStateOf(false) }
    val getResponse = jobsViewModel.getResponse.value

    LaunchedEffect(key1 = id){
        if (jobsViewModel.jobId.value != id){
            jobsViewModel.setJobId(id = id)
            jobDetail(false)
            jobsViewModel.get(
                phone = appViewModel.phoneValue.value,
                password = appViewModel.passwordValue.value,
                id = id
            )
        }
    }

    LaunchedEffect(key1 = getResponse){
        when(getResponse){
            is Resource.Success -> {
                isLoading = false
                when(getResponse.data?.message){
                    "Job Got Successfully!" -> {
                        jobDetail(true)
                        setFabNavigate(getResponse.data.job.id, getResponse.data.job.status)
                        delay(200)
                        submissionsViewModel.emptyGetAllResponse()
                    }
                    else -> { errorIsOpen = true }
                }
            }
            is Resource.Error -> { errorIsOpen = true }
            is Resource.Loading -> { isLoading = true; jobDetail(false) }
            else -> { }
        }
    }

    DisplayAlertDialog(
        openDialog = errorIsOpen,
        title = "مشکل ارتباط با سرور",
        description = "متاسفانه نتوانستیم به سرور متصل شویم، لطفا اینترنت خود را برسی کنید.",
        onCloseClicked = { },
        onYesClicked = {
            if (
                (getResponse is Resource.Success && getResponse.data?.message != "Job Got Successfully!") ||
                        getResponse is Resource.Error
            ){
                jobsViewModel.get(
                    phone = appViewModel.phoneValue.value,
                    password = appViewModel.passwordValue.value,
                    id = id
                )
            }
            errorIsOpen = false
        },
        yesTitle = "تلاش مجدد",
        noTitle = "",
        dismiss = false
    )

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = isLoading),
        onRefresh = {
            jobsViewModel.get(
                phone = appViewModel.phoneValue.value,
                password = appViewModel.passwordValue.value,
                id = id
            )
        }
    ) {
        if (getResponse?.data != null && !isLoading){
            JobDetailsScreenContent(
                job = getResponse.data.job,
                files = getResponse.data.files,
                baseUrl = BASE_URL + (appViewModel.baseInfoValue.value?.data?.baseInfo?.uploadUrl?: UPLOAD_URL)
            )
        }else{
            JobDetailScreenShimmer()
        }
    }
}