package com.tinyDeveloper.na.ui.screens.main.home.notification

import android.util.Log
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.tinyDeveloper.na.R
import com.tinyDeveloper.na.domain.models.response.notifications.Notification
import com.tinyDeveloper.na.ui.component.DisplayAlertDialog
import com.tinyDeveloper.na.ui.screens.AppViewModel
import com.tinyDeveloper.na.ui.screens.main.home.NotificationsViewModel
import com.tinyDeveloper.na.ui.theme.MEDIUM_PADDING
import com.tinyDeveloper.na.ui.theme.NOTIFICATION_DETAILS_SCREEN_IMAGE_HEIGHT
import com.tinyDeveloper.na.ui.theme.SMALL_PADDING
import com.tinyDeveloper.na.ui.theme.ShimmerColor
import com.tinyDeveloper.na.utils.Constants.BASE_URL
import com.tinyDeveloper.na.utils.Resource
import com.tinyDeveloper.na.utils.toTime

@Composable
fun NotificationDetailsScreen(
    appViewModel: AppViewModel,
    notificationsViewModel: NotificationsViewModel = hiltViewModel(),
    id: String?
){
    var isLoading by remember { mutableStateOf(false) }
    val notification = notificationsViewModel.getNotificationResponse.value
    var errorIsOpen by remember { mutableStateOf(false) }
    LaunchedEffect(key1 = id){
        if(id != null && id != notificationsViewModel.notificationId.value){
            if (id != "-1"){
                notificationsViewModel.getNotification(
                    phone = appViewModel.phoneValue.value,
                    password = appViewModel.passwordValue.value,
                    id = id
                )
            }
            notificationsViewModel.setNotificationId(id = id)
        }
    }

    LaunchedEffect(key1 = notification){
        Log.i("notification", notification?.data?.message.toString())
        when(notification){
            is Resource.Success -> {
                isLoading = false
                when (notification.data?.message){
                    "Notification Got Successfully!" -> {}
                    else -> { errorIsOpen = true }
                }
            }
            is Resource.Error -> { errorIsOpen = true; isLoading = false }
            is Resource.Loading -> { isLoading = true }
            else -> { }
        }
    }

    DisplayAlertDialog(
        openDialog = errorIsOpen,
        title = "مشکل ارتباط با سرور",
        description = "متاسفانه نتوانستیم به سرور متصل شویم، لطفا اینترنت خود را برسی کنید.",
        onCloseClicked = { },
        onYesClicked = {
            notificationsViewModel.getNotification(
                phone = appViewModel.phoneValue.value,
                password = appViewModel.passwordValue.value,
                id = notificationsViewModel.notificationId.value
            )
            errorIsOpen = false
        },
        yesTitle = "تلاش مجدد",
        noTitle = "",
        dismiss = false
    )
    SetNotificationDetailContent(
        isLoading = isLoading,
        modifier = Modifier.fillMaxSize(),
        notification = notification?.data?.notification,
        baseUrl = "$BASE_URL${appViewModel.baseInfoValue.value?.data?.baseInfo?.uploadUrl}",
        swipeToRefresh = {
            notificationsViewModel.getNotification(
                phone = appViewModel.phoneValue.value,
                password = appViewModel.passwordValue.value,
                id = notificationsViewModel.notificationId.value
            )
        }
    )
}

@Composable
fun SetNotificationDetailContent(
    isLoading: Boolean,
    modifier: Modifier,
    notification: Notification?,
    baseUrl: String,
    swipeToRefresh: () -> Unit
){
    SwipeRefresh(state = rememberSwipeRefreshState(isRefreshing = isLoading), onRefresh = swipeToRefresh) {
        if (isLoading || notification == null){
            NotificationDetailShimmerAnimated()
        }else {
            NotificationDetailContent(
                modifier = modifier,
                notification = notification,
                baseUrl = baseUrl
            )
        }
    }
}

@Composable
fun NotificationDetailShimmerAnimated(){
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
    NotificationDetailShimmerContent(alpha = alphaAnimate)
}

@Composable
fun NotificationDetailShimmerContent(alpha: Float){
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = scrollState),
    ) {
        Image(
            modifier = Modifier
                .alpha(alpha = alpha)
                .fillMaxWidth()
                .height(NOTIFICATION_DETAILS_SCREEN_IMAGE_HEIGHT),
            painter = painterResource(id = R.drawable.ic_place_holder),
            contentDescription = "Notification Detail Screen",
            contentScale = ContentScale.Crop,
        )
        Spacer(modifier = Modifier.height(MEDIUM_PADDING))
        Surface(modifier = Modifier
            .height(24.dp)
            .width(200.dp)
            .padding(start = MEDIUM_PADDING),
            shape = RoundedCornerShape(50.dp)
        ) {
            Box(modifier = Modifier
                .fillMaxSize()
                .alpha(alpha = alpha)
                .background(ShimmerColor)
            )
        }
        Spacer(modifier = Modifier.height(SMALL_PADDING))
        Surface(modifier = Modifier
            .height(12.dp)
            .width(75.dp)
            .padding(start = MEDIUM_PADDING),
            shape = RoundedCornerShape(50.dp)
        ) {
            Box(modifier = Modifier
                .fillMaxSize()
                .alpha(alpha = alpha)
                .background(ShimmerColor)
            )
        }
        Spacer(modifier = Modifier.height(MEDIUM_PADDING))
        repeat(8){
            Surface(modifier = Modifier
                .padding(horizontal = MEDIUM_PADDING)
                .fillMaxWidth()
                .height(16.dp),
                shape = RoundedCornerShape(50.dp)
            ) {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .alpha(alpha = alpha)
                    .background(ShimmerColor)
                )
            }
            Spacer(modifier = Modifier.height(SMALL_PADDING))
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun NotificationDetailContent(
    modifier: Modifier,
    notification: Notification,
    baseUrl: String
){
    val scrollState = rememberScrollState()
    Surface(modifier = modifier) {
        Column(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = scrollState)
        ) {
            if (notification.image.isNotEmpty()){
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(NOTIFICATION_DETAILS_SCREEN_IMAGE_HEIGHT),
                    painter = rememberImagePainter(baseUrl + notification.image),
                    contentDescription = "Notification Detail Screen",
                    contentScale = ContentScale.Crop,
                )
            }
            Spacer(modifier = Modifier.height(MEDIUM_PADDING))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MEDIUM_PADDING),
                text = notification.title,
                fontSize = androidx.compose.material3.MaterialTheme.typography.titleMedium.fontSize,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MEDIUM_PADDING),
                text = notification.date.toTime(),
                fontSize = androidx.compose.material3.MaterialTheme.typography.bodySmall.fontSize,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(MEDIUM_PADDING))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MEDIUM_PADDING),
                text = notification.description,
                fontSize = androidx.compose.material3.MaterialTheme.typography.bodyLarge.fontSize,
            )
            Spacer(modifier = Modifier.height(MEDIUM_PADDING))
        }
    }
}