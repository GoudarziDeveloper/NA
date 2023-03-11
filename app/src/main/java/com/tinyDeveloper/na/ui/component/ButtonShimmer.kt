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
import androidx.compose.ui.unit.dp
import com.tinyDeveloper.na.ui.theme.ShimmerColor

@Composable
fun ButtonShimmer(modifier: Modifier, alpha: Float){
    Surface(
        modifier = Modifier
            .height(46.dp),
        shape = RoundedCornerShape(30.dp)
    ){
        Box(
            modifier = modifier
                .alpha(alpha = alpha)
                .fillMaxSize()
                .background(ShimmerColor)
        )
    }
}