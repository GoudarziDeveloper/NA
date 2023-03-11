package com.tinyDeveloper.na.ui.screens.main.jobs.job.submissions.submisson

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.tinyDeveloper.na.ui.theme.*

@Composable
fun SubmissionShimmerEffect(){
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
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(MEDIUM_PADDING),
        horizontalAlignment = Alignment.Start) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .alpha(alpha = alphaAnimate),
            shape = RoundedCornerShape(LARGE_PADDING)
        ) {
            Box(modifier = Modifier.fillMaxSize().background(ShimmerColor))
        }
        Spacer(modifier = Modifier.height(MEDIUM_PADDING))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(MEDIUM_PADDING)) {
            repeat(2){
                Surface(modifier = Modifier
                    .weight(1f)
                    .height(DROP_DOWN_HEIGHT)
                    .alpha(alpha = alphaAnimate),
                    shape = RoundedCornerShape(LARGE_PADDING)
                ) {
                    Box(modifier = Modifier.fillMaxSize().background(ShimmerColor))
                }
            }
        }
        Spacer(modifier = Modifier.height(MEDIUM_PADDING))
        Surface(
            modifier = Modifier
                .width(80.dp)
                .height(42.dp)
                .alpha(alpha = alphaAnimate),
            shape = RoundedCornerShape(LARGEST_PADDING)
        ) {
            Box(modifier = Modifier.fillMaxSize().background(ShimmerColor))
        }
    }
}