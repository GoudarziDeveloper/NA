package com.tinyDeveloper.na.ui.screens.main.jobs.job.submissions.submisson

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.tinyDeveloper.na.ui.component.*
import com.tinyDeveloper.na.ui.screens.AppViewModel
import com.tinyDeveloper.na.ui.screens.main.jobs.JobsViewModel
import com.tinyDeveloper.na.ui.screens.main.jobs.job.submissions.SubmissionsViewModel
import com.tinyDeveloper.na.utils.Constants.BASE_URL
import com.tinyDeveloper.na.utils.Resource

@Composable
fun SubmissionScreen(
    appViewModel: AppViewModel,
    submissionsViewModel: SubmissionsViewModel,
    submissionViewModel: SubmissionsViewModel = hiltViewModel(),
    jobsViewModel: JobsViewModel,
    id: String,
    jobStatus: String?
){
    var errorIsOpen by remember{ mutableStateOf(false) }
    var isLoadingFile by remember{ mutableStateOf(false) }
    var added by rememberSaveable { mutableStateOf(false) }
    var isLoading by remember{ mutableStateOf(false) }
    var submissionId by remember{ mutableStateOf("-1") }
    val isAdmin = appViewModel.roles.value?.getSubmissions == "1"
    var deleteIsOpen by remember{ mutableStateOf(false) }
    var deleteId by remember{ mutableStateOf("") }
    var jobId by remember{ mutableStateOf("") }
    var userId by remember{ mutableStateOf("") }

    val getSubmissionResponse = submissionViewModel.getSubmissionResponse.value
    LaunchedEffect(key1 = getSubmissionResponse){
        when(getSubmissionResponse){
            is Resource.Success -> {
                isLoading = false
                when(getSubmissionResponse.data?.message){
                    "Submission Got Successfully!" -> {
                        userId = getSubmissionResponse.data.submission.userId
                        jobId = getSubmissionResponse.data.submission.jobId
                        submissionId = getSubmissionResponse.data.submission.id
                        submissionViewModel.updateFields(
                            description = getSubmissionResponse.data.submission.description,
                            jobId = getSubmissionResponse.data.submission.jobId,
                            score = getSubmissionResponse.data.submission.score,
                            status = getSubmissionResponse.data.submission.status,
                        )
                        submissionViewModel.updateFiles(getSubmissionResponse.data.files)
                        added = true
                    }
                    "Submission By This Id Not Founded!" -> {

                    }
                    "This Phone Number Have No Submission!" -> {}
                    else -> { errorIsOpen = true }
                }
            }
            is Resource.Error -> { isLoading = false; errorIsOpen = true }
            is Resource.Loading -> { isLoading = true }
            else -> {
                if (isAdmin){
                    submissionViewModel.getSubmission(
                        phone = appViewModel.phoneValue.value,
                        password = appViewModel.passwordValue.value,
                        id = id
                    )
                }else {
                    submissionViewModel.getUserSubmission(
                        phone = appViewModel.phoneValue.value,
                        password = appViewModel.passwordValue.value,
                        jobId = id
                    )
                }
            }
        }
    }

    val basicResponse = submissionViewModel.basicResponse.value
    LaunchedEffect(key1 = basicResponse){
        when(basicResponse){
            is Resource.Success -> {
                isLoading = false
                when(basicResponse.data?.message){
                    "Submission Updated Successfully!" -> {
                        if (isAdmin){
                            submissionsViewModel.getAll(
                                phone = appViewModel.phoneValue.value,
                                password = appViewModel.passwordValue.value,
                                id = jobId
                            )
                        }
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
                    "File Deleted Successfully!" -> {
                        submissionViewModel.deleteInFiles(deleteId); deleteId = ""
                    }
                    else -> { errorIsOpen = true }
                }
            }
            is Resource.Error -> {
                errorIsOpen = true; isLoading = false
            }
            is Resource.Loading -> { isLoading = true }
            else -> {}
        }
    }
    LaunchedEffect(key1 = deleteId){
        isLoadingFile = deleteId.isNotEmpty()
    }

    val addFileResponse = submissionViewModel.addFileResponse.value
    LaunchedEffect(key1 = addFileResponse){
        when(addFileResponse){
            is Resource.Success -> {
                isLoadingFile = false
                when(addFileResponse.data?.message){
                    "File Added Successfully!" -> {
                        submissionViewModel.addToFiles(file = addFileResponse.data.file)
                    }
                    else -> { errorIsOpen = true }
                }
            }
            is Resource.Error -> { isLoadingFile = false; errorIsOpen = true }
            is Resource.Loading -> { isLoadingFile = true }
            else -> {}
        }
    }

    val addSubmissionResponse = submissionViewModel.addSubmissionResponse.value
    LaunchedEffect(key1 = addSubmissionResponse){
        when(addSubmissionResponse){
            is Resource.Success -> {
                isLoading = false
                when(addSubmissionResponse.data?.message){
                    "Submission Added Successfully!" -> {
                        submissionId = addSubmissionResponse.data.submission?.id?:""
                        jobId = addSubmissionResponse.data.submission?.jobId?:""
                        userId = addSubmissionResponse.data.submission?.userId?:""
                        added = true
                        if (isAdmin){
                            submissionsViewModel.getAll(
                                phone = appViewModel.phoneValue.value,
                                password = appViewModel.passwordValue.value,
                                id = id
                            )
                        }
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
            if ((getSubmissionResponse is Resource.Success &&
                        getSubmissionResponse.data?.message != "Submission Got Successfully!") ||
                        getSubmissionResponse is Resource.Error){
                if (isAdmin){
                    submissionViewModel.getSubmission(
                        phone = appViewModel.phoneValue.value,
                        password = appViewModel.passwordValue.value,
                        id = id
                    )
                }else {
                    submissionViewModel.getUserSubmission(
                        phone = appViewModel.phoneValue.value,
                        password = appViewModel.passwordValue.value,
                        jobId = id
                    )
                }
            }
            if (deleteId.isEmpty() &&
                ((basicResponse is Resource.Success &&
                        basicResponse.data?.message != "Submission Updated Successfully!") ||
                        basicResponse is Resource.Error)
            ){
                submissionViewModel.update(
                    submissionId = submissionId,
                    phone = appViewModel.phoneValue.value,
                    password = appViewModel.passwordValue.value,
                    jobId = jobId,
                    userId = userId
                )
            }
            if (deleteId.isNotEmpty() &&
                ((basicResponse is Resource.Success &&
                        basicResponse.data?.message != "File Deleted Successfully!") ||
                        basicResponse is Resource.Error)
            ){
                submissionViewModel.deleteFile(
                    id = deleteId,
                    phone = appViewModel.phoneValue.value,
                    password = appViewModel.passwordValue.value
                )
            }
            if ((addFileResponse is Resource.Success &&
                        addFileResponse.data?.message != "File Added Successfully!") ||
                                addFileResponse is Resource.Error)
            {
                submissionViewModel.addFile()
            }
            if ((addSubmissionResponse is Resource.Success &&
                        addSubmissionResponse.data?.message != "Submission Added Successfully!") ||
                                addSubmissionResponse is Resource.Error)
            {
                submissionViewModel.add(
                    phone = appViewModel.phoneValue.value,
                    password = appViewModel.passwordValue.value,
                    id = id,
                    userId = "-1"
                )
            }
            errorIsOpen = false },
        yesTitle = "تلاش مجدد",
        noTitle = "",
        dismiss = false
    )

    DisplayAlertDialog(
        openDialog = deleteIsOpen,
        title = "حذف کامل فایل",
        description = "فایل به طور کامل پاک شود؟ این کار قابل بازگشت نیست.",
        onCloseClicked = {
            deleteIsOpen = false
            deleteId = ""
        },
        onYesClicked = {
            deleteIsOpen = false
            submissionViewModel.deleteFile(
                id = deleteId,
                phone = appViewModel.phoneValue.value,
                password = appViewModel.passwordValue.value
            )
        },
        yesTitle = "بله",
        noTitle = "خیر",
        dismiss = true
    )

   SwipeRefresh(
       state = rememberSwipeRefreshState(isRefreshing = isLoading),
       onRefresh = {
           if (isAdmin){
               submissionViewModel.getSubmission(
                   phone = appViewModel.phoneValue.value,
                   password = appViewModel.passwordValue.value,
                   id = id
               )
           }else {
               submissionViewModel.getUserSubmission(
                   phone = appViewModel.phoneValue.value,
                   password = appViewModel.passwordValue.value,
                   jobId = id
               )
           }
       }
   ) {
       if (isLoading && !isLoadingFile){
           SubmissionShimmerEffect()
       }else{
           SubmissionScreenContent(
               submissionsViewModel = submissionViewModel,
               isLoading = isLoading,
               added = added,
               id = id,
               files = submissionViewModel.files.value,
               phone = appViewModel.phoneValue.value,
               password = appViewModel.passwordValue.value,
               submissionId = submissionId,
               baseUrl = BASE_URL + (appViewModel.baseInfoValue.value?.data?.baseInfo?.uploadUrl),
               deleteFileClicked = { deleteIsOpen = true; deleteId = it },
               isAdmin = isAdmin,
               jobId = jobId,
               userId = userId,
               isLoadingFile = isLoadingFile,
               deletedId = deleteId,
               deleteIsOpen = deleteIsOpen,
               addFilePermission = appViewModel.roles.value?.addFile == "1" || jobId == id,
               deleteFilePermission = appViewModel.roles.value?.deleteFile == "1" || jobId == id,
               jobStatus = jobStatus
           )
       }
   }
}

