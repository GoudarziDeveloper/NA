package com.tinyDeveloper.na.ui.screens.main.jobs.job.submissions

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.tinyDeveloper.na.R
import com.tinyDeveloper.na.domain.models.response.submissions.Submission
import com.tinyDeveloper.na.ui.theme.MEDIUM_PADDING
import com.tinyDeveloper.na.utils.*

@OptIn(ExperimentalCoilApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SubmissionItem(
    submission: Submission,
    baseUrl: String,
    navigateToSubmissionScreen: (jobId: String, jobStatus: String) -> Unit,
    maxScore: Int,
    openUserImage: (Painter) -> Unit
){
    var imagePainter by remember{ mutableStateOf<Painter?>(null) }
    Card(
        modifier = Modifier
            .fillMaxSize(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp, hoveredElevation = 0.dp),
        onClick = { navigateToSubmissionScreen(submission.id, submission.status) }
    ) {
        Box(modifier = Modifier.fillMaxSize()){
            Box(modifier = Modifier
                .clip(RoundedCornerShape(topStart = MEDIUM_PADDING))
                .background(submission.status.toColor())
                .padding(MEDIUM_PADDING)
                .align(Alignment.BottomEnd)
            ) {
                Text(
                    text = submission.status.setStatus(),
                    color = Color.White,
                    fontSize = MaterialTheme.typography.labelSmall.fontSize
                )
            }
            Box(modifier = Modifier
                .clip(RoundedCornerShape(bottomStart = MEDIUM_PADDING))
                .background(submission.score.toSubmissionScoreColor(max = maxScore))
                .padding(MEDIUM_PADDING)
                .align(Alignment.TopEnd)
            ) {
                Text(
                    text = "امتیاز: ${submission.score}",
                    color = Color.White,
                    fontSize = MaterialTheme.typography.labelSmall.fontSize
                )
            }
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(MEDIUM_PADDING)
            ) {
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    if (submission.image.isNotEmpty())
                        imagePainter = rememberImagePainter(baseUrl + submission.image)
                    else
                        imagePainter = painterResource(id = R.drawable.ic_user)
                    Image(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .clickable { openUserImage(imagePainter!!) },
                        painter = imagePainter!!,
                        contentDescription = "User Image",
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(MEDIUM_PADDING))
                    Column {
                        Text(
                            text = "${submission.firstName} ${submission.lastName}",
                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = submission.baseName,
                            fontSize = MaterialTheme.typography.bodyMedium.fontSize
                        )
                    }
                }
                Spacer(modifier = Modifier.height(MEDIUM_PADDING))
                if (submission.description.isNotEmpty()){
                    Text(
                        text = submission.description,
                        fontSize = MaterialTheme.typography.bodySmall.fontSize,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 3
                    )
                    Spacer(modifier = Modifier.height(MEDIUM_PADDING))
                }
                Text(

                    text = submission.date.toTime(),
                    fontSize = MaterialTheme.typography.labelSmall.fontSize,
                )
            }
        }
    }
}