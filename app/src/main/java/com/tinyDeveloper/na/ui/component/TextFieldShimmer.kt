package com.tinyDeveloper.na.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.Dp
import com.tinyDeveloper.na.ui.theme.DROP_DOWN_HEIGHT
import com.tinyDeveloper.na.ui.theme.LARGE_PADDING
import com.tinyDeveloper.na.ui.theme.ShimmerColor

@Composable
fun TextFieldShimmer(modifier: Modifier,alpha: Float, height: Dp = DROP_DOWN_HEIGHT){
    Surface(
        modifier = modifier
            .height(height = height)
            .alpha(alpha = alpha),
        shape = RoundedCornerShape(LARGE_PADDING)
    ) {
        Box(modifier = Modifier.fillMaxSize().background(ShimmerColor))
    }
}