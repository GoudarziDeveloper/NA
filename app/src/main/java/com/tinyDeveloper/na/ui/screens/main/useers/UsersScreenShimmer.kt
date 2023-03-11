package com.tinyDeveloper.na.ui.screens.main.useers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.tinyDeveloper.na.ui.component.TextShimmer
import com.tinyDeveloper.na.ui.component.rememberAnimatedFloat
import com.tinyDeveloper.na.ui.theme.*

@Composable
fun UsersScreenShimmer(){
    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
        .padding(MEDIUM_PADDING),
        verticalArrangement = Arrangement.spacedBy(MEDIUM_PADDING)
    ) {
        repeat(10){
            UsersScreenItemShimmer()
        }
    }
}

@Composable
fun UsersScreenItemShimmer(){
    val alpha = rememberAnimatedFloat()
    Card(modifier = Modifier.fillMaxWidth()) {
        Box(modifier = Modifier.fillMaxSize()){
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(MEDIUM_PADDING), verticalAlignment = Alignment.Top) {
                Surface(modifier = Modifier
                    .size(80.dp)
                    .alpha(alpha = alpha), shape = CircleShape) {
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .background(ShimmerColor))
                }
                Spacer(modifier = Modifier.width(MEDIUM_PADDING))
                Column {
                    TextShimmer(
                        modifier = Modifier.fillMaxWidth(0.5f),
                        alpha = alpha
                    )
                    Spacer(modifier = Modifier.height(MEDIUM_PADDING))
                    TextShimmer(modifier = Modifier.fillMaxWidth(0.7f), alpha = alpha, height = SHIMMER_EXTRA_SMALL)
                }
            }
            TextShimmer(
                modifier = Modifier
                    .width(SHIMMER_LARGE_HEIGHT)
                    .align(Alignment.BottomCenter),
                alpha = alpha,
                height = SHIMMER_NORMAL_SMALL_TEXT,
                shape = RoundedCornerShape(topEnd = MEDIUM_PADDING, topStart = MEDIUM_PADDING)
            )
            TextShimmer(
                modifier = Modifier
                    .width(SHIMMER_LARGE_HEIGHT)
                    .align(Alignment.BottomEnd),
                alpha = alpha,
                height = SHIMMER_NORMAL_SMALL_TEXT,
                shape = RoundedCornerShape(topStart = MEDIUM_PADDING)
            )
        }
    }
}