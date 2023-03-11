package com.tinyDeveloper.na.ui.screens.main.jobs.job.submissions.submisson

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
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
import com.tinyDeveloper.na.R
import com.tinyDeveloper.na.domain.models.response.files.File
import com.tinyDeveloper.na.ui.component.*
import com.tinyDeveloper.na.ui.screens.main.jobs.job.submissions.SubmissionsViewModel
import com.tinyDeveloper.na.ui.theme.LARGEST_PADDING
import com.tinyDeveloper.na.ui.theme.LARGE_PADDING
import com.tinyDeveloper.na.ui.theme.MEDIUM_PADDING
import com.tinyDeveloper.na.ui.theme.SMALL_PADDING
import com.tinyDeveloper.na.utils.StateValues
import com.tinyDeveloper.na.utils.bitmapToBase64
import com.tinyDeveloper.na.utils.toAdvanceStateValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubmissionScreenContent(
    submissionsViewModel: SubmissionsViewModel,
    isLoading: Boolean,
    added: Boolean,
    id: String,
    files: List<File>?,
    phone: String,
    password: String,
    submissionId: String,
    baseUrl: String,
    deleteFileClicked: (String) -> Unit,
    isAdmin: Boolean,
    jobId: String,
    userId: String,
    isLoadingFile: Boolean,
    deletedId: String,
    deleteIsOpen: Boolean,
    addFilePermission: Boolean,
    deleteFilePermission: Boolean,
    jobStatus: String?
){
    var galleryLaunch by remember { mutableStateOf(false) }
    PickImageGallery(isLaunch = galleryLaunch, launched = { galleryLaunch = false }) {
        if (it != null) {
            galleryLaunch = false
            submissionsViewModel.updateFileFields(submissionId = submissionId, file = it.bitmapToBase64(), phone = phone, password = password)
            submissionsViewModel.addFile()
        }
    }

    var fileLaunch by remember { mutableStateOf(false) }
    SelectFile(isLaunch = fileLaunch, launched = { fileLaunch = false }) {
        if (it != null) {
            fileLaunch = false
            submissionsViewModel.updateFileFields(submissionId = submissionId, file = it, phone = phone, password = password)
            submissionsViewModel.addFile()
        }
    }

    var cameraLauncher by remember { mutableStateOf(false) }
    TakePhoto(
        isLaunch = cameraLauncher,
        launched = { cameraLauncher = false },
        onBitmapReady = { bitmap: Bitmap?, success: Boolean ->
            if (success && bitmap != null){
                submissionsViewModel.updateFileFields(submissionId = submissionId, file = bitmap.bitmapToBase64(), phone = phone, password = password)
                submissionsViewModel.addFile()
            }
        }
    )

    val scrollState = rememberScrollState()
    val submission by submissionsViewModel.submissionRequest
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = MEDIUM_PADDING)
        .verticalScroll(state = scrollState)
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            value = submission?.description?:"",
            onValueChange = { submissionsViewModel.updateFields(description = it) },
            label = { Text(text = "توضیحات") }
        )
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){
            StatusDropDown(
                modifier = Modifier
                    .weight(0.5f)
                    .padding(top = MEDIUM_PADDING),
                advanceItem = submission?.status.toAdvanceStateValue(),
                onItemSelected = { submissionsViewModel.updateFields(status = it) },
                enabled = isAdmin,
                isBasic = false
            )
            Spacer(modifier = Modifier.width(MEDIUM_PADDING))
            OutlinedTextField(
                modifier = Modifier.weight(0.5f),
                value = submission?.score?:"",
                onValueChange = { submissionsViewModel.updateFields(score = it) },
                label = { Text(text = "امتیاز") },
                enabled = isAdmin
            )
        }
        Spacer(modifier = Modifier.height(MEDIUM_PADDING))
        Button(
            enabled = !isLoading && (jobStatus != StateValues.HIDE.value || isAdmin),
            onClick = {
                if (isAdmin){
                    if (added){
                        submissionsViewModel.update(
                            submissionId = submissionId,
                            phone = phone,
                            password = password,
                            jobId = jobId,
                            userId = userId
                        )
                    }else {
                        submissionsViewModel.add(phone = phone, password = password, id = id, userId = userId)
                    }
                }else {
                    if (added){
                        submissionsViewModel.update(
                            submissionId = submissionId,
                            phone = phone,
                            password = password,
                            jobId = jobId,
                            userId = userId
                        )
                    }else {
                        submissionsViewModel.add(phone = phone, password = password, id = id, userId = "-1")
                    }
                }
            }
        ) {
            Text(text = "ثبت", fontSize = MaterialTheme.typography.titleSmall.fontSize)
        }

        Spacer(modifier = Modifier.height(MEDIUM_PADDING))
        if (added){
            if (addFilePermission){
                Spacer(modifier = Modifier.height(MEDIUM_PADDING))
                Box(modifier = Modifier.fillMaxWidth()){
                    Text(text = "پیوست:", modifier = Modifier.align(Alignment.CenterStart))
                    Row(modifier = Modifier
                        .align(Alignment.CenterEnd)) {
                        IconButton(
                            modifier = Modifier.size(LARGEST_PADDING),
                            onClick = { galleryLaunch = true },
                            enabled = !isLoadingFile && (jobStatus != StateValues.HIDE.value || isAdmin)
                        ) {
                            Icon(painter = painterResource(id = R.drawable.ic_gallery), contentDescription = "Gallery Icon")
                        }
                        Spacer(modifier = Modifier.width(LARGE_PADDING))
                        IconButton(
                            modifier = Modifier.size(LARGEST_PADDING),
                            onClick = { cameraLauncher = true },
                            enabled = !isLoadingFile && (jobStatus != StateValues.HIDE.value || isAdmin)
                        ) {
                            Icon(painter = painterResource(id = R.drawable.ic_camera), contentDescription = "Camera Icon")
                        }
                        Spacer(modifier = Modifier.width(LARGE_PADDING))
                        IconButton(
                            modifier = Modifier.size(LARGEST_PADDING),
                            onClick = { fileLaunch = true },
                            enabled = !isLoadingFile && (jobStatus != StateValues.HIDE.value || isAdmin)
                        ) {
                            Icon(painter = painterResource(id = R.drawable.ic_select_file), contentDescription = "Select File Icon")
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(SMALL_PADDING))
            val alphaAnimate = rememberAnimatedFloat()
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