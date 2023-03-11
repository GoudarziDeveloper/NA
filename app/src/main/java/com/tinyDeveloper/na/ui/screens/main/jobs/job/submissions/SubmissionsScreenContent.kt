package com.tinyDeveloper.na.ui.screens.main.jobs.job.submissions

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.tinyDeveloper.na.R
import com.tinyDeveloper.na.domain.models.response.submissions.Submission
import com.tinyDeveloper.na.ui.component.EmptyScreen
import com.tinyDeveloper.na.ui.component.ImageShowAlertDialog
import com.tinyDeveloper.na.ui.theme.MEDIUM_PADDING
import com.tinyDeveloper.na.utils.maxScore

@Composable
fun SubmissionsScreenContent(
    submissions: List<Submission>?,
    isLoading: Boolean,
    baseUrl: String,
    navigateToSubmissionScreen: (jobId: String, jobStatus: String) -> Unit,
    swipeToRefresh: () -> Unit
){
    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = isLoading),
        onRefresh = swipeToRefresh
    ) {
        if (!isLoading && submissions != null && submissions.isNotEmpty()){
            var imageShowIsOpen by remember{ mutableStateOf(false) }
            var imagePainter by remember { mutableStateOf<Painter?>(null) }
            if (imagePainter != null){
                ImageShowAlertDialog(isOpen = imageShowIsOpen, imagePainter = imagePainter!!) {
                    imageShowIsOpen = false
                }
            }
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(MEDIUM_PADDING),
                verticalArrangement = Arrangement.spacedBy(MEDIUM_PADDING)
            ){
                items(submissions){
                    SubmissionItem(
                        submission = it,
                        baseUrl = baseUrl,
                        navigateToSubmissionScreen = navigateToSubmissionScreen,
                        submissions.maxScore(),
                        openUserImage = { image -> imageShowIsOpen = true; imagePainter = image}
                    )
                }
            }
       }else if(isLoading || submissions == null){
            Column(modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(MEDIUM_PADDING),
                verticalArrangement = Arrangement.spacedBy(MEDIUM_PADDING)
            ) {
                repeat(1){
                    SubmissionItemShimmer()
                }
            }
        }else {
            EmptyScreen(
                image = R.drawable.ic_empty_screen2,
                text = "در حال حاضر برای این کارخواسته چیزی تحویل داده نشده است!"
            )
        }
    }
}

