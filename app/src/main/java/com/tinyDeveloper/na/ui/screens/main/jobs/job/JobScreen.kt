package com.tinyDeveloper.na.ui.screens.main.jobs.job

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.tinyDeveloper.na.R
import com.tinyDeveloper.na.domain.models.response.files.File
import com.tinyDeveloper.na.ui.component.*
import com.tinyDeveloper.na.ui.screens.AppViewModel
import com.tinyDeveloper.na.ui.screens.main.jobs.JobsViewModel
import com.tinyDeveloper.na.ui.theme.LARGEST_PADDING
import com.tinyDeveloper.na.ui.theme.LARGE_PADDING
import com.tinyDeveloper.na.ui.theme.MEDIUM_PADDING
import com.tinyDeveloper.na.ui.theme.SMALL_PADDING
import com.tinyDeveloper.na.utils.Constants.BASE_URL
import com.tinyDeveloper.na.utils.Constants.UPLOAD_URL
import com.tinyDeveloper.na.utils.Resource
import com.tinyDeveloper.na.utils.bitmapToBase64
import com.tinyDeveloper.na.utils.toStateValue
import com.tinyDeveloper.na.utils.toTimeValue

@Composable
fun JobScreen(
    appViewModel: AppViewModel,
    adminStatus: (Boolean) -> Unit,
    jobsViewModel: JobsViewModel = hiltViewModel(),
    id: String,
    navigateToJobsScreen: () -> Unit
){
    val addResponse = jobsViewModel.addResponse.value
    var isLoading by remember{ mutableStateOf(false) }
    var isLoadingFile by remember{ mutableStateOf(false) }
    var errorIsOpen by remember{ mutableStateOf(false) }
    var added by remember { mutableStateOf(false) }
    var jobTitle by remember{ mutableStateOf("") }
    val jobId by jobsViewModel.jobId
    val files by jobsViewModel.files
    val getResponse = jobsViewModel.getResponse.value
    var deleteIsOpen by remember{ mutableStateOf(false) }
    var deleteId by remember{ mutableStateOf("") }

    LaunchedEffect(key1 = id){
        if (id != "-1" && jobsViewModel.jobId.value != id){
            jobsViewModel.setJobId(id = id)
            jobsViewModel.get(
                phone = appViewModel.phoneValue.value,
                password = appViewModel.passwordValue.value,
                id = id
            )
        } else if(id == "-1"){
            jobsViewModel.updateFields(empty = true)
            jobsViewModel.updateFileFields(empty = true)
            jobsViewModel.emptyFiles()
            jobsViewModel.setJobId("-1")
        }
    }

    LaunchedEffect(key1 = getResponse){
        when(getResponse){
            is Resource.Success -> {
                isLoading = false
                when(getResponse.data?.message){
                    "Job Got Successfully!" -> {
                        if (id != "-1" && jobId == id){
                            jobsViewModel.updateFields(
                                centerId = getResponse.data.job.centerId,
                                description = getResponse.data.job.description,
                                maxShowTime = getResponse.data.job.maxShowTime,
                                status = getResponse.data.job.status,
                                title = getResponse.data.job.title
                            )
                            jobsViewModel.updateFiles(files = getResponse.data.files)
                            jobTitle = getResponse.data.job.title
                        }
                    }
                    else -> { errorIsOpen = true }
                }
            }
            is Resource.Error -> { errorIsOpen = true }
            is Resource.Loading -> { isLoading = true }
            else -> { }
        }
    }

    LaunchedEffect(key1 = addResponse){
        when(addResponse){
            is Resource.Success -> {
                isLoading = false
                when(addResponse.data?.message){
                    "Job Added Successfully!" -> {
                        added = true
                        jobTitle = jobsViewModel.addRequest.value?.title?:""
                        jobsViewModel.setJobId(jobsViewModel.addResponse.value?.data?.jobId?.toString()?:"-1")
                        jobsViewModel.getAll(
                            phone = appViewModel.phoneValue.value,
                            password = appViewModel.passwordValue.value
                        )
                        jobsViewModel.emptyAddResponse()
                    }
                    else -> { errorIsOpen = true }
                }
            }
            is Resource.Error -> { errorIsOpen = true; isLoading = false }
            is Resource.Loading -> { isLoading = true }
            else -> { }
        }
    }

    val addFileResponse = jobsViewModel.addFileResponse.value
    LaunchedEffect(key1 = addFileResponse){
        when(addFileResponse){
            is Resource.Success -> {
                isLoading = false
                isLoadingFile = false
                when(addFileResponse.data?.message){
                    "File Added Successfully!" -> {
                        jobsViewModel.addToFiles(file = addFileResponse.data.file)
                        jobsViewModel.emptyFileResponse()
                    }
                    else -> { errorIsOpen = true; isLoadingFile = false }
                }
            }
            is Resource.Error -> { isLoading = false; errorIsOpen = true; isLoadingFile = false }
            is Resource.Loading -> { isLoading = true; isLoadingFile = true }
            else -> {}
        }
    }

    val basicResponse = jobsViewModel.basicResponse.value
    LaunchedEffect(key1 = basicResponse){
        when(basicResponse){
            is Resource.Success -> {
                isLoading = false
                when(basicResponse.data?.message){
                    "Job Updated Successfully!" -> {
                        if (!added) navigateToJobsScreen()
                        jobsViewModel.getAll(phone = appViewModel.phoneValue.value, password = appViewModel.passwordValue.value)
                        jobsViewModel.emptyBasicResponse()
                        jobsViewModel.setJobId(id = "-1")
                    }
                    "File Deleted Successfully!" -> {
                        jobsViewModel.deleteInFiles(deleteId); deleteId = ""; jobsViewModel.emptyBasicResponse()
                    }
                    else -> { errorIsOpen = true }
                }
            }
            is Resource.Error -> { errorIsOpen = true; isLoading = false }
            is Resource.Loading -> { isLoading = true }
            else -> {}
        }
    }

    LaunchedEffect(key1 = deleteId){
        isLoadingFile = deleteId.isNotEmpty()
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
                if (id != "-1"){
                    jobsViewModel.get(
                        phone = appViewModel.phoneValue.value,
                        password = appViewModel.passwordValue.value,
                        id = id
                    )
                }
            }
            if ((addResponse is Resource.Success && addResponse.data?.message != "Job Added Successfully!") ||
                    addResponse is Resource.Error){
                if(!added && id == "-1") jobsViewModel.add()
            }
            if ((addFileResponse is Resource.Success && addFileResponse.data?.message != "File Added Successfully!") ||
                    addFileResponse is Resource.Error){
                jobsViewModel.addFile()
            }
            if (deleteId.isEmpty() && ((basicResponse is Resource.Success && basicResponse.data?.message != "Job Updated Successfully!") ||
                        (basicResponse is Resource.Error))){
                if (id != "-1") jobsViewModel.update(id = id)
                Log.i("delete file", "update")
            }
            if (deleteId.isNotEmpty() && (
                        (basicResponse is Resource.Success &&
                        basicResponse.data?.message != "File Deleted Successfully!") ||
                        basicResponse is Resource.Error)
            ){
                Log.i("delete file", deleteId)
                jobsViewModel.deleteFile(
                    id = deleteId,
                    phone = appViewModel.phoneValue.value,
                    password = appViewModel.passwordValue.value
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
        title = "حذف کامل فایل",
        description = "فایل به طور کامل پاک شود؟ این کار قابل بازگشت نیست.",
        onCloseClicked = {
            deleteIsOpen = false
            deleteId = ""
        },
        onYesClicked = {
            deleteIsOpen = false
            jobsViewModel.deleteFile(
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
        swipeEnabled = id != "-1",
        state = rememberSwipeRefreshState(isRefreshing = isLoading),
        onRefresh = {
            jobsViewModel.get(
                phone = appViewModel.phoneValue.value,
                password = appViewModel.passwordValue.value,
                id = id
            )
        }
    ) {
        if (!isLoading || isLoadingFile){
            JobScreenContent(
                jobsViewModel = jobsViewModel,
                phone = appViewModel.phoneValue.value,
                password = appViewModel.passwordValue.value,
                added = added,
                isLoading = isLoading,
                jobTitle = jobTitle,
                files = files,
                jobId = jobId,
                baseUrl = BASE_URL + (appViewModel.baseInfoValue.value?.data?.baseInfo?.uploadUrl?:UPLOAD_URL),
                id = if (id != "-1") id else jobId,
                deleteFileClicked = {
                    deleteId = it
                    deleteIsOpen = true
                },
                deleteIsOpen = deleteIsOpen,
                deletedId = deleteId,
                isLoadingFile = isLoadingFile,
                addFilePermission = appViewModel.roles.value?.addFile == "1",
                deleteFilePermission = appViewModel.roles.value?.deleteFile == "1"
            )
        }else {

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobScreenContent(
    jobsViewModel: JobsViewModel,
    phone: String,
    password: String,
    added: Boolean,
    isLoading: Boolean,
    files: List<File>?,
    jobTitle: String,
    jobId: String,
    baseUrl: String,
    id: String,
    deleteFileClicked: (String) -> Unit,
    isLoadingFile: Boolean,
    deletedId: String,
    deleteIsOpen: Boolean,
    addFilePermission: Boolean,
    deleteFilePermission: Boolean
){
    var galleryLaunch by remember { mutableStateOf(false) }
    PickImageGallery(isLaunch = galleryLaunch, launched = { galleryLaunch = false }) {
        if (it != null) {
            galleryLaunch = false
            jobsViewModel.updateFileFields(jobId = jobId, file = it.bitmapToBase64(), phone = phone, password = password)
            jobsViewModel.addFile()
        }
    }

    var fileLaunch by remember { mutableStateOf(false) }
    SelectFile(isLaunch = fileLaunch, launched = { fileLaunch = false }) {
        if (it != null) {
            fileLaunch = false
            jobsViewModel.updateFileFields(jobId = jobId, file = it, phone = phone, password = password)
            jobsViewModel.addFile()
        }
    }

    var cameraLauncher by remember { mutableStateOf(false) }
    TakePhoto(
        isLaunch = cameraLauncher,
        launched = { cameraLauncher = false },
        onBitmapReady = { bitmap: Bitmap?, success: Boolean ->
            if (success && bitmap != null){
                jobsViewModel.updateFileFields(jobId = jobId, file = bitmap.bitmapToBase64(), phone = phone, password = password)
                jobsViewModel.addFile()
            }
        }
    )
    val scrollState = rememberScrollState()
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = MEDIUM_PADDING)
        .verticalScroll(scrollState)
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = jobsViewModel.addRequest.value?.title?:"",
            onValueChange = { jobsViewModel.updateFields(title = it) },
            label = { Text(text = "عنوان") }
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            value = jobsViewModel.addRequest.value?.description?:"",
            onValueChange = { jobsViewModel.updateFields(description = it) },
            label = { Text(text = "توضیحات") }
        )
        Spacer(modifier = Modifier.height(MEDIUM_PADDING))
        StatusDropDown(
            item = jobsViewModel.addRequest.value?.status.toStateValue(),
            onItemSelected = { jobsViewModel.updateFields(status = it) }
        )
        Spacer(modifier = Modifier.height(MEDIUM_PADDING))
        TimeDropDown(
            item = jobsViewModel.addRequest.value?.maxShowTime.toTimeValue(),
            onItemSelected = { jobsViewModel.updateFields(maxShowTime = it) }
        )
        Spacer(modifier = Modifier.height(MEDIUM_PADDING))
        Button(
            onClick = {
                jobsViewModel.updateFields(phone = phone, password = password)
                if(!added && id == "-1") jobsViewModel.add()
                else jobsViewModel.update(id = id)
            },
            enabled = !isLoading
        ) {
            Text(
                text = "ثبت",
                fontSize = MaterialTheme.typography.titleMedium.fontSize
            )
        }
        Spacer(modifier = Modifier.height(MEDIUM_PADDING))
        if (added || id != "-1"){
            if (addFilePermission){
                Spacer(modifier = Modifier.height(MEDIUM_PADDING))
                Box(modifier = Modifier.fillMaxWidth()){
                    Text(text = "پیوست:", modifier = Modifier.align(Alignment.CenterStart))
                    Row(modifier = Modifier
                        .align(Alignment.CenterEnd)) {
                        IconButton(modifier = Modifier.size(LARGEST_PADDING), onClick = { galleryLaunch = true }, enabled = !isLoading) {
                            Icon(painter = painterResource(id = R.drawable.ic_gallery), contentDescription = "Gallery Icon")
                        }
                        Spacer(modifier = Modifier.width(LARGE_PADDING))
                        IconButton(modifier = Modifier.size(LARGEST_PADDING), onClick = { cameraLauncher = true }, enabled = !isLoading) {
                            Icon(painter = painterResource(id = R.drawable.ic_camera), contentDescription = "Camera Icon")
                        }
                        Spacer(modifier = Modifier.width(LARGE_PADDING))
                        IconButton(modifier = Modifier.size(LARGEST_PADDING), onClick = { fileLaunch = true }, enabled = !isLoading) {
                            Icon(painter = painterResource(id = R.drawable.ic_select_file), contentDescription = "Select File Icon")
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(MEDIUM_PADDING))
            val transaction = rememberInfiniteTransition()
            val alphaAnimate by transaction.animateFloat(
                initialValue = 1f,
                targetValue = 0.2f,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = 500,
                        easing = FastOutLinearInEasing
                    ),
                    repeatMode = RepeatMode.Reverse
                )
            )
            if (files != null){
                if (isLoadingFile && deletedId.isNotEmpty() && !deleteIsOpen){
                    for (item in files){
                        FileItemShimmer(alphaAnimate)
                        Spacer(modifier = Modifier.height(MEDIUM_PADDING))
                    }
                }else{
                    for (item in files){
                        val app = LocalContext.current as Activity
                        FileItem(file = item, jobTitle = null, itemClicked = {
                            val webIntent = Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(baseUrl + item.src)
                            )
                            app.startActivity(webIntent)
                        },
                            deleteFile = deleteFilePermission,
                            deleteFileClicked = deleteFileClicked)
                        Spacer(modifier = Modifier.height(MEDIUM_PADDING))
                    }
                    if (isLoadingFile && deletedId.isEmpty()){
                        FileItemShimmer(alpha = alphaAnimate)
                        Spacer(modifier = Modifier.height(MEDIUM_PADDING))
                    }
                }
            }
        }
    }
}