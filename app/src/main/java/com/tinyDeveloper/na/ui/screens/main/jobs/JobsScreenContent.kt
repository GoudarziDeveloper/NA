package com.tinyDeveloper.na.ui.screens.main.jobs

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.tinyDeveloper.na.R
import com.tinyDeveloper.na.domain.models.response.jobs.get_all.Job
import com.tinyDeveloper.na.ui.component.EmptyScreen
import com.tinyDeveloper.na.ui.screens.main.jobs.job.JobsScreenSimmer
import com.tinyDeveloper.na.ui.theme.*
import com.tinyDeveloper.na.utils.*
import kotlinx.coroutines.delay

@Composable
fun JobsScreenContent(
    jobs: List<Job>?,
    isLoading: Boolean,
    isAdmin: Boolean,
    isDelete: Boolean,
    isUpdate: Boolean,
    onDeleteClicked: (id: String) -> Unit,
    onUpdateClicked: (id: String) -> Unit,
    itemClicked: (String) -> Unit,
    swipeToRefresh: () -> Unit,
){
    SwipeRefresh(state = rememberSwipeRefreshState(isRefreshing = isLoading), onRefresh = swipeToRefresh) {
        if (!isLoading && jobs != null){
            if (jobs.isNotEmpty()){
                JobsList(
                    jobs = jobs,
                    isDelete = isDelete,
                    isUpdate = isUpdate,
                    isAdmin = isAdmin,
                    onDeleteClicked = onDeleteClicked,
                    onUpdateClicked = onUpdateClicked,
                    itemClicked = itemClicked
                )
            }else {
                EmptyScreen(
                    image = R.drawable.ic_empty_screen2,
                    text = "در حال حاضر کارخواسته ای وجود ندارد."
                )
            }
        }else{
            JobsScreenSimmer()
        }
    }
}

@Composable
fun JobsList(
    jobs: List<Job>,
    isDelete: Boolean,
    isUpdate: Boolean,
    isAdmin: Boolean,
    onDeleteClicked: (id: String) -> Unit,
    onUpdateClicked: (id: String) -> Unit,
    itemClicked: (String) -> Unit
){
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(MEDIUM_PADDING),
        verticalArrangement = Arrangement.spacedBy(MEDIUM_PADDING)
    ){
        items(jobs){job ->
            JobsListItem(
                job = job,
                isDelete = isDelete,
                isUpdate = isUpdate,
                isAdmin = isAdmin,
                onDeleteClicked = onDeleteClicked,
                onUpdateClicked = onUpdateClicked,
                itemClicked = itemClicked
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobsListItem(
    job: Job,
    isDelete: Boolean,
    isUpdate: Boolean,
    isAdmin: Boolean,
    onDeleteClicked: (id: String) -> Unit,
    onUpdateClicked: (id: String) -> Unit,
    itemClicked: (String) -> Unit
){
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp, hoveredElevation = 0.dp),
        onClick = { itemClicked(job.id) }
    ){
        Box(modifier = Modifier.fillMaxSize()){
            Row(modifier = Modifier
                .padding(MEDIUM_PADDING)
                .align(Alignment.TopEnd)
            ) {
                if (isUpdate){
                    IconButton(
                        modifier = Modifier.size(24.dp),
                        onClick = { onUpdateClicked(job.id) }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Update Icon"
                        )
                    }
                }
                Spacer(modifier = Modifier.width(MEDIUM_PADDING))
                if (isDelete){
                    IconButton(
                        modifier = Modifier.size(24.dp),
                        onClick = { onDeleteClicked(job.id) }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete Icon"
                        )
                    }
                }
            }
            Box(modifier = Modifier
                .clip(RoundedCornerShape(topStart = MEDIUM_PADDING))
                .background(job.status.toColor())
                .padding(MEDIUM_PADDING)
                .align(Alignment.BottomEnd)
            ) {
                Text(
                    text = job.status.setStatus(),
                    color = Color.White,
                    fontSize = MaterialTheme.typography.labelSmall.fontSize
                )
            }
            Box(modifier = Modifier
                .clip(RoundedCornerShape(topStart = MEDIUM_PADDING, topEnd = MEDIUM_PADDING))
                .background(job.complete.toColor())
                .padding(MEDIUM_PADDING)
                .align(Alignment.BottomCenter)
            ) {
                Text(
                    text = if (isAdmin) job.complete.setCompleteAdmin() else job.complete.setCompleteUser(),
                    color = Color.White,
                    fontSize = MaterialTheme.typography.labelSmall.fontSize
                )
            }
            Box(modifier = Modifier
                .clip(RoundedCornerShape(topEnd = MEDIUM_PADDING))
                .background(job.completeStatus.toCompleteStatusColor(isAdmin))
                .padding(MEDIUM_PADDING)
                .align(Alignment.BottomStart)
            ) {
                Text(
                    text =
                        if (isAdmin){
                            if(job.completeStatus == "0"){
                                "برسی شده"
                            }else{
                                "${job.completeStatus} مورد برسی نشده"
                            }
                        }else{
                             if(job.completeStatus.isEmpty()){
                                 "امتیاز: در انتظار تحویل"
                             }else{
                                 "امتیاز: ${job.completeStatus}"
                             }
                        },
                    color = Color.White,
                    fontSize = MaterialTheme.typography.labelSmall.fontSize
                )
            }
            Column(modifier = Modifier
                .padding(MEDIUM_PADDING)
                .fillMaxSize()) {
                Text(
                    text = job.title,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Text(
                    text = "${job.date.toTime()} - فرصت: ${job.maxShowTime.setExtraTime(job.date)}",
                    fontSize = MaterialTheme.typography.labelMedium.fontSize,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(MEDIUM_PADDING))
                Text(
                    text = job.description,
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 3
                )
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}