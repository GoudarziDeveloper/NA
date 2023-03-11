package com.tinyDeveloper.na.ui.component

import androidx.compose.animation.core.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue

@Composable
fun rememberAnimatedFloat(): Float{
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
    return alphaAnimate
}