package com.tinyDeveloper.na.ui.screens.main.jobs.job.submissions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tinyDeveloper.na.ui.component.TextShimmer
import com.tinyDeveloper.na.ui.component.rememberAnimatedFloat
import com.tinyDeveloper.na.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubmissionItemShimmer(){
    val alpha = rememberAnimatedFloat()
    Card(modifier = Modifier.fillMaxWidth()) {
        Box(modifier = Modifier.fillMaxSize()){
            Column(modifier = Modifier.fillMaxWidth().padding(MEDIUM_PADDING)) {
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Surface(modifier = Modifier.size(56.dp), shape = CircleShape) {
                        Box(modifier = Modifier
                            .fillMaxSize()
                            .background(ShimmerColor))
                    }
                    Spacer(modifier = Modifier.width(MEDIUM_PADDING))
                    Column {
                        TextShimmer(modifier = Modifier.fillMaxWidth(0.4f), alpha = alpha, height = SHIMMER_NORMAL_TEXT)
                        Spacer(modifier = Modifier.height(SMALL_PADDING))
                        TextShimmer(modifier = Modifier.fillMaxWidth(0.6f), alpha = alpha)
                    }
                }
                Spacer(modifier = Modifier.height(MEDIUM_PADDING))
                repeat(3){
                    TextShimmer(modifier = Modifier.fillMaxWidth(), alpha = alpha, height = SHIMMER_EXTRA_SMALL)
                    Spacer(modifier = Modifier.height(SMALL_PADDING))
                }
                Spacer(modifier = Modifier.height(SMALL_PADDING))
                TextShimmer(modifier = Modifier.fillMaxWidth(0.2f), alpha = alpha, height = SHIMMER_EXTRA_SMALL)
                Spacer(modifier = Modifier.height(SMALL_PADDING))
            }
            TextShimmer(
                modifier = Modifier
                    .width(SHIMMER_NORMAL_HEIGHT)
                    .align(Alignment.BottomEnd),
                alpha = alpha,
                height = SHIMMER_NORMAL_SMALL_TEXT,
                shape = RoundedCornerShape(topStart = MEDIUM_PADDING)
            )
            TextShimmer(
                modifier = Modifier
                    .width(SHIMMER_NORMAL_HEIGHT)
                    .align(Alignment.TopEnd),
                alpha = alpha,
                height = SHIMMER_NORMAL_SMALL_TEXT,
                shape = RoundedCornerShape(bottomStart = MEDIUM_PADDING)
            )
        }
    }
}