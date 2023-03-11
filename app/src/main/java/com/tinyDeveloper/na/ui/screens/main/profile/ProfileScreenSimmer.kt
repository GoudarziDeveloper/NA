package com.tinyDeveloper.na.ui.screens.main.profile

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.tinyDeveloper.na.R
import com.tinyDeveloper.na.ui.theme.*

@Composable
fun ProfileScreenShimmer(){
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
    val scrollState = rememberScrollState()
    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(state = scrollState)
        .padding(all = MEDIUM_PADDING),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(modifier = Modifier.height(EXTRA_LARGE_PADDING))
        Surface(
            modifier = Modifier.size(PROFILE_IMAGE_HEIGHT),
            shape = RoundedCornerShape(LARGEST_PADDING)
        ) {
            Image(
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(alpha = alphaAnimate),
                painter = painterResource(id = R.drawable.ic_place_holder),
                contentDescription = "Profile Image",
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.height(LARGE_PADDING))
        ProfileScreenShimmerItem(
            modifier = Modifier
                .fillMaxWidth()
                .height(SHIMMER_NORMAL_HEIGHT),
            alpha = alphaAnimate
        )
        Spacer(modifier = Modifier.height(LARGE_PADDING))
        repeat(4){
            Row(Modifier.fillMaxWidth()) {
                ProfileScreenShimmerItem(
                    modifier = Modifier
                        .weight(1f)
                        .height(SHIMMER_NORMAL_HEIGHT),
                    alpha = alphaAnimate
                )
                Spacer(modifier = Modifier.width(MEDIUM_PADDING))
                ProfileScreenShimmerItem(
                    modifier = Modifier
                        .weight(1f)
                        .height(SHIMMER_NORMAL_HEIGHT),
                    alpha = alphaAnimate
                )
            }
            Spacer(modifier = Modifier.height(LARGE_PADDING))
        }
        ProfileScreenShimmerItem(
            modifier = Modifier
                .fillMaxWidth()
                .height(SHIMMER_LARGE_HEIGHT),
            alpha = alphaAnimate
        )
        Spacer(modifier = Modifier.height(LARGE_PADDING))
        Row(modifier = Modifier.fillMaxWidth()) {
            ProfileScreenShimmerItem(
                modifier = Modifier
                    .weight(1f)
                    .height(SHIMMER_SMALL_HEIGHT),
                alpha = alphaAnimate,
                button = true
            )
            Spacer(modifier = Modifier.width(MEDIUM_PADDING))
            ProfileScreenShimmerItem(
                modifier = Modifier
                    .weight(1f)
                    .height(SHIMMER_SMALL_HEIGHT),
                alpha = alphaAnimate,
                button = true
            )
        }
        Spacer(modifier = Modifier.height(LARGE_PADDING))
    }
}

@Composable
fun ProfileScreenShimmerItem(
    modifier: Modifier,
    alpha: Float,
    button: Boolean = false
){
    Surface(
        modifier = modifier,
        shape = if (button) RoundedCornerShape(LARGEST_PADDING)  else MaterialTheme.shapes.small
    ) {
        Box(modifier = Modifier
            .alpha(alpha = alpha)
            .fillMaxSize()
            .background(ShimmerColor))
    }
}