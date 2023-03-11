package com.tinyDeveloper.na.ui.screens.main.bases

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.tinyDeveloper.na.ui.component.TextShimmer
import com.tinyDeveloper.na.ui.component.rememberAnimatedFloat
import com.tinyDeveloper.na.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasesScreenItemShimmer(){
    val alpha = rememberAnimatedFloat()
    Card(modifier = Modifier.fillMaxWidth()) {
        Box(modifier = Modifier.fillMaxSize()){
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(MEDIUM_PADDING)) {
                TextShimmer(
                    modifier = Modifier.fillMaxWidth(0.4f),
                    alpha = alpha,
                    height = SHIMMER_NORMAL_TEXT
                )
                Spacer(modifier = Modifier.height(MEDIUM_PADDING))
                TextShimmer(
                    modifier = Modifier.fillMaxWidth(0.7f),
                    alpha = alpha,
                    height = SHIMMER_EXTRA_SMALL
                )
            }
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
