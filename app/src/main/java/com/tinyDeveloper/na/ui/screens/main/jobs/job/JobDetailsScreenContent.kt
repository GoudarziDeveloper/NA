package com.tinyDeveloper.na.ui.screens.main.jobs.job.submissions

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.tinyDeveloper.na.domain.models.response.files.File
import com.tinyDeveloper.na.domain.models.response.jobs.get_job.Job
import com.tinyDeveloper.na.ui.component.FileItem
import com.tinyDeveloper.na.ui.theme.*
import com.tinyDeveloper.na.utils.setExtraTime
import com.tinyDeveloper.na.utils.toTime

@Composable
fun JobDetailsScreenContent(
    job: Job,
    files: List<File>,
    baseUrl: String
){
    val scrollState = rememberScrollState()
    val app = LocalContext.current as Activity
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = MEDIUM_PADDING)
        .padding(top = MEDIUM_PADDING)
        .verticalScroll(state = scrollState)
    ) {
        Text(
            text = job.title,
            fontSize = MaterialTheme.typography.titleMedium.fontSize,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = job.date.toTime(),
            fontSize = MaterialTheme.typography.labelMedium.fontSize
        )
        Spacer(modifier = Modifier.height(MEDIUM_PADDING))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = job.description,
            fontSize = MaterialTheme.typography.bodyLarge.fontSize
        )
        Spacer(modifier = Modifier.height(MEDIUM_PADDING))
        Text(
            text = "فرصت تا: ${job.maxShowTime.setExtraTime(job.date)}",
            fontSize = MaterialTheme.typography.labelLarge.fontSize,
            fontWeight = FontWeight.Bold
        )
        if (files.isNotEmpty()){
            Spacer(modifier = Modifier.height(LARGE_PADDING))
            Text(
                text = "پیوست:",
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
            )
            Spacer(modifier = Modifier.height(MEDIUM_PADDING))

            for (file in files){
                FileItem(
                    file = file,
                    jobTitle = job.title,
                    itemClicked = {
                        val webIntent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(baseUrl + it)
                        )
                        app.startActivity(webIntent)
                    },
                    deleteFile = false,
                    deleteFileClicked = {}
                )
                Spacer(modifier = Modifier.height(MEDIUM_PADDING))
            }
        }
    }
}