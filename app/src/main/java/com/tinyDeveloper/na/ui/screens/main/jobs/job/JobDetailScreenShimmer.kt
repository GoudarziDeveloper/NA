package com.tinyDeveloper.na.ui.screens.main.jobs.job

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.tinyDeveloper.na.ui.component.TextShimmer
import com.tinyDeveloper.na.ui.component.rememberAnimatedFloat
import com.tinyDeveloper.na.ui.theme.MEDIUM_PADDING
import com.tinyDeveloper.na.ui.theme.SHIMMER_EXTRA_SMALL
import com.tinyDeveloper.na.ui.theme.SHIMMER_NORMAL_TEXT
import com.tinyDeveloper.na.ui.theme.SMALL_PADDING

@Composable
fun JobDetailScreenShimmer(){
    val alpha = rememberAnimatedFloat()
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(MEDIUM_PADDING)
        .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(MEDIUM_PADDING))
        TextShimmer(modifier = Modifier.fillMaxWidth(0.3f), alpha = alpha, height = SHIMMER_NORMAL_TEXT)
        Spacer(modifier = Modifier.height(SMALL_PADDING))
        TextShimmer(modifier = Modifier.fillMaxWidth(0.2f), alpha = alpha, height = SHIMMER_EXTRA_SMALL)
        Spacer(modifier = Modifier.height(MEDIUM_PADDING))
        repeat(10){
            TextShimmer(modifier = Modifier.fillMaxWidth(), alpha = alpha)
            Spacer(modifier = Modifier.height(SMALL_PADDING))
        }
        TextShimmer(modifier = Modifier.fillMaxWidth(0.7f), alpha = alpha)
        Spacer(modifier = Modifier.height(MEDIUM_PADDING))
        TextShimmer(modifier = Modifier.fillMaxWidth(0.3f), alpha = alpha)
    }
}