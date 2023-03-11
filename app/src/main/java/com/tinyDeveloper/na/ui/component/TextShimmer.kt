package com.tinyDeveloper.na.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.Dp
import com.tinyDeveloper.na.ui.theme.MEDIUM_PADDING
import com.tinyDeveloper.na.ui.theme.SHIMMER_SMALL_TEXT
import com.tinyDeveloper.na.ui.theme.ShimmerColor

@Composable
fun TextShimmer(
    modifier: Modifier,
    alpha: Float,
    height: Dp = SHIMMER_SMALL_TEXT,
    shape: RoundedCornerShape = RoundedCornerShape(MEDIUM_PADDING)
){
    Surface(
        modifier = modifier
            .height(height = height)
            .alpha(alpha = alpha),
        shape = shape
    ) {
        Box(modifier = Modifier.fillMaxSize().background(ShimmerColor))
    }
}