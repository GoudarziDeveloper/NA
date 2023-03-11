package com.tinyDeveloper.na.ui.screens.main.home

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.tinyDeveloper.na.R
import com.tinyDeveloper.na.ui.theme.*
import com.tinyDeveloper.na.utils.Constants.HALF_ALPHA

@Composable
fun NotificationListShimmer(){
    LazyColumn(
        contentPadding = PaddingValues(all = MEDIUM_PADDING),
        verticalArrangement = Arrangement.spacedBy(MEDIUM_PADDING)
    ){
        items(count = 3){
            NotificationItemShimmerAnimated()
        }
    }
}

@Composable
fun NotificationItemShimmerAnimated(){
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
    NotificationItemShimmer(alpha = alphaAnimate)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationItemShimmer(alpha: Float){
    Card(modifier = Modifier
        .fillMaxWidth()
        .height(NOTIFICATION_ITEM_HEIGHT)
    ) {
        Box(modifier = Modifier.fillMaxSize()){
            Image(
                modifier = Modifier.fillMaxSize().alpha(HALF_ALPHA),
                painter = painterResource(id = R.drawable.ic_placeholder),
                contentDescription = "NotificationShimmerItemImage",
                contentScale = ContentScale.Crop,
            )
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .align(Alignment.BottomCenter)
                .background(Color.DarkGray.copy(alpha = HALF_ALPHA))
            ) {
                Column(verticalArrangement = Arrangement.SpaceBetween) {
                    Spacer(modifier = Modifier.height(MEDIUM_PADDING))
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .height(28.dp)
                            .alpha(alpha = alpha)
                            .padding(horizontal = MEDIUM_PADDING),
                        color = Color.LightGray.copy(HALF_ALPHA),
                        shape = RoundedCornerShape(MEDIUM_PADDING)
                    ) {}
                    Spacer(modifier = Modifier.height(MEDIUM_PADDING))
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth(0.2f)
                            .height(16.dp)
                            .alpha(alpha = alpha)
                            .padding(horizontal = MEDIUM_PADDING),
                        color = Color.LightGray.copy(HALF_ALPHA),
                        shape = RoundedCornerShape(MEDIUM_PADDING)
                    ) {}
                    Spacer(modifier = Modifier.height(MEDIUM_PADDING))
                    Spacer(modifier = Modifier.height(MEDIUM_PADDING))
                    repeat(times = 3){
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth(1f)
                                .height(22.dp)
                                .alpha(alpha = alpha)
                                .padding(horizontal = MEDIUM_PADDING),
                            color = Color.LightGray.copy(HALF_ALPHA),
                            shape = RoundedCornerShape(MEDIUM_PADDING)
                        ) {}
                        Spacer(modifier = Modifier.height(SMALL_PADDING))
                    }
                }
            }
        }
    }
}