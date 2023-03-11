package com.tinyDeveloper.na.ui.screens.main.jobs.job

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tinyDeveloper.na.ui.component.TextShimmer
import com.tinyDeveloper.na.ui.component.rememberAnimatedFloat
import com.tinyDeveloper.na.ui.theme.*


@Composable
fun JobsScreenSimmer(){
    val alpha = rememberAnimatedFloat()
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(MEDIUM_PADDING),
        verticalArrangement = Arrangement.spacedBy(
            MEDIUM_PADDING
        )
    ){
        items(1){
            JobsScreenItemShimmer(alpha = alpha)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobsScreenItemShimmer(alpha: Float) {
    Card(modifier = Modifier
        .fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(MEDIUM_PADDING)
            ) {
                Spacer(modifier = Modifier.height(SMALL_PADDING))
                TextShimmer(
                    modifier = Modifier.fillMaxWidth(0.3f),
                    alpha = alpha,
                    height = SHIMMER_SMALL_TEXT
                )
                Spacer(modifier = Modifier.height(SMALL_PADDING))
                TextShimmer(
                    modifier = Modifier.fillMaxWidth(0.4f),
                    alpha = alpha,
                    height = SHIMMER_EXTRA_SMALL
                )
                Spacer(modifier = Modifier.height(LARGE_PADDING))
                repeat(3){
                    TextShimmer(modifier = Modifier.fillMaxWidth(), alpha = alpha, height = SHIMMER_MEDIUM_SMALL)
                    Spacer(modifier = Modifier.height(SMALL_PADDING))
                }
                Spacer(modifier = Modifier.height(EXTRA_LARGE_PADDING))
                Spacer(modifier = Modifier.height(LARGE_PADDING))
            }
            TextShimmer(
                modifier = Modifier
                    .width(120.dp)
                    .align(Alignment.BottomStart),
                alpha = alpha,
                height = SHIMMER_NORMAL_TEXT,
                shape = RoundedCornerShape(topEnd = MEDIUM_PADDING)
            )
            TextShimmer(
                modifier = Modifier
                    .width(120.dp)
                    .align(Alignment.BottomEnd),
                alpha = alpha,
                height = SHIMMER_NORMAL_TEXT,
                shape = RoundedCornerShape(topStart = MEDIUM_PADDING)
            )
            TextShimmer(
                modifier = Modifier
                    .width(SHIMMER_NORMAL_HEIGHT)
                    .align(Alignment.BottomCenter),
                alpha = alpha,
                height = SHIMMER_NORMAL_TEXT,
                shape = RoundedCornerShape(topEnd = MEDIUM_PADDING, topStart = MEDIUM_PADDING)
            )
        }
    }
}