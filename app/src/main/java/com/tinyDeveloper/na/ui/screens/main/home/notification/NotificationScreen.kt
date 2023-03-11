package com.tinyDeveloper.na.ui.screens.main.home.notification

import android.graphics.Bitmap
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.tinyDeveloper.na.R
import com.tinyDeveloper.na.domain.models.response.notifications.GetNotificationResponse
import com.tinyDeveloper.na.ui.component.*
import com.tinyDeveloper.na.ui.screens.AppViewModel
import com.tinyDeveloper.na.ui.screens.main.home.NotificationsViewModel
import com.tinyDeveloper.na.ui.theme.*
import com.tinyDeveloper.na.utils.*
import com.tinyDeveloper.na.utils.Constants.BASE_URL


@Composable
fun NotificationScreen(
    appViewModel: AppViewModel,
    notificationsViewModel: NotificationsViewModel = hiltViewModel(),
    navigateToNotificationsScreen: () -> Unit,
    id: String?
){
    val isAdmin =
        appViewModel.roles.value != null &&
                appViewModel.roles.value!!.getNotifications == "1"

    val phone = appViewModel.phoneValue.value
    val password = appViewModel.passwordValue.value

    var isLoading by remember{ mutableStateOf(false) }
    val image = notificationsViewModel.image.value
    val getNotificationResponse = notificationsViewModel.getNotificationResponse.value
    val notificationResponse = notificationsViewModel.notificationResponse.value
    var errorIsOpen by rememberSaveable{ mutableStateOf(false) }
    var messageIsOpened by remember{ mutableStateOf(false) }
    var isAdd by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = id){
        if (notificationsViewModel.notificationId.value != id){
            notificationsViewModel.updateImage(null)
        }
        if(id != null && id != "-1"){
            if (notificationsViewModel.notificationId.value != id){
                notificationsViewModel.setNotificationId(id = id)
                notificationsViewModel.getNotification(
                    phone = appViewModel.phoneValue.value,
                    password = appViewModel.passwordValue.value,
                    id = id
                )
            }
        }else{
            notificationsViewModel.emptyImage()
            notificationsViewModel.notificationFieldUpdate(empty = true)
        }
    }

    LaunchedEffect(key1 = notificationResponse){
        when(notificationResponse){
            is Resource.Success -> {
                isLoading = false
                when(notificationResponse.data?.message){
                    "Notification Added Successfully!" -> {
                        if (isAdmin){
                            notificationsViewModel.getAllCommonNotifications(phone = phone, password = password)
                            notificationsViewModel.getAllPrivateNotifications(phone = phone, password = password)
                        }else {
                            notificationsViewModel.getCommonNotifications(phone = phone, password = password)
                            notificationsViewModel.getPrivateNotifications(phone = phone, password = password)
                        }
                        notificationsViewModel.emptyNotificationResponse()
                        navigateToNotificationsScreen()
                    }
                    "Notification Updated Successfully!" -> {
                        if (isAdmin){
                            notificationsViewModel.getAllCommonNotifications(phone = phone, password = password)
                            notificationsViewModel.getAllPrivateNotifications(phone = phone, password = password)
                        }else {
                            notificationsViewModel.getCommonNotifications(phone = phone, password = password)
                            notificationsViewModel.getPrivateNotifications(phone = phone, password = password)
                        }
                        notificationsViewModel.setNotificationId("")
                        notificationsViewModel.emptyNotificationResponse()
                        navigateToNotificationsScreen()
                    }
                    else -> { errorIsOpen = true }
                }
            }
            is Resource.Error -> {
                isLoading = false
                errorIsOpen = true
            }
            is Resource.Loading -> {
                isLoading = true
            }
            else -> {}
        }
    }

    LaunchedEffect(key1 = getNotificationResponse){
        when(getNotificationResponse){
            is Resource.Success -> {
                isLoading = false
                when(getNotificationResponse.data?.message){
                    "Notification Got Successfully!" -> {
                        if (id != "-1" && notificationsViewModel.notificationId.value == id){
                            notificationsViewModel.notificationFieldUpdate(
                                baseId = getNotificationResponse.data.notification.baseId,
                                title = getNotificationResponse.data.notification.title,
                                description = getNotificationResponse.data.notification.description,
                                maxShowTime = getNotificationResponse.data.notification.maxShowTime,
                                status = getNotificationResponse.data.notification.status
                            )
                        }else {
                            notificationsViewModel.notificationFieldUpdate(empty = true)
                            notificationsViewModel.emptyImage()
                            notificationsViewModel.setNotificationId("-1")
                            isAdd = true
                        }
                    }
                    "This Phone Number Have No Access!" -> { messageIsOpened = true }
                    else -> { errorIsOpen = true }
                }
            }
            is Resource.Error -> {
                errorIsOpen = true
                isLoading = false
            }
            is Resource.Loading -> { isLoading = true }
            else -> { }
        }
    }

    LaunchedEffect(key1 = image) {
        image?.let {
            notificationsViewModel.notificationFieldUpdate(
                image = it.bitmapToBase64()
            )
        }
    }

    DisplayAlertDialog(
        openDialog = errorIsOpen,
        title = "مشکل ارتباط با سرور",
        description = "متاسفانه نتوانستیم به سرور متصل شویم، لطفا اینترنت خود را برسی کنید.",
        onCloseClicked = { },
        onYesClicked = {
            if ((getNotificationResponse is Resource.Success &&
                        getNotificationResponse.data?.message != "Notification Got Successfully!") ||
                getNotificationResponse is Resource.Error
            ){
                if (id != null && id != "-1"){
                    notificationsViewModel.getNotification(
                        phone = appViewModel.phoneValue.value,
                        password = appViewModel.passwordValue.value,
                        id = id
                    )
                }
            }
            if (
                (notificationResponse is Resource.Success &&
                notificationResponse.data?.message != "Notification Added Successfully!") ||
                (notificationResponse is Resource.Error && notificationsViewModel.notificationId.value == "-1")
            ){
                notificationsViewModel.addNotification(phone = phone, password = password)
            }
            if (
                (notificationResponse is Resource.Success &&
                        notificationResponse.data?.message != "Notification Updated Successfully!") ||
                (notificationResponse is Resource.Error &&
                        !isAdd && notificationsViewModel.notificationId.value != "-1")
            ){
                if (image == null){
                    notificationsViewModel.updateNotification(
                        notificationsViewModel.notificationId.value, imageNotUpdate = true,
                        phone = phone,
                        password = password
                    )
                }else {
                    notificationsViewModel.updateNotification(
                        notificationsViewModel.notificationId.value, imageNotUpdate = false,
                        phone = phone,
                        password = password
                    )
                }
            }
            errorIsOpen = false
        },
        yesTitle = "تلاش مجدد",
        noTitle = "",
        dismiss = false
    )

    DisplayAlertDialog(
        openDialog = messageIsOpened,
        title = "سطح دسترسی",
        description = "سرور دسترسی شما را تایید نکرد ممکن است دسترسی شما محدود شده باشد، میتوانید مجدد تلاش کنید",
        onCloseClicked = { messageIsOpened = false },
        onYesClicked = { messageIsOpened = false },
        yesTitle = "تلاش مجدد",
        noTitle = "باشه",
        dismiss = false
    )

    var galleryLaunch by remember { mutableStateOf(false) }
    PickImageGallery(isLaunch = galleryLaunch, launched = { galleryLaunch = false }) {
        if (it != null) {
            galleryLaunch = false
            notificationsViewModel.updateImage(it)
        }
    }

    var cameraLauncher by remember { mutableStateOf(false) }
    TakePhoto(
        isLaunch = cameraLauncher,
        launched = {cameraLauncher = false},
        onBitmapReady = { bitmap: Bitmap?, success: Boolean ->
            if (success && bitmap != null){
                notificationsViewModel.updateImage(bitmap)
            }
        }
    )

    NotificationScreenContentManager(
        isLoading = isLoading,
        image = image,
        getNotificationResponse = getNotificationResponse,
        appViewModel = appViewModel,
        notificationsViewModel = notificationsViewModel,
        galleryLauncher = { galleryLaunch = true },
        cameraLauncher = { cameraLauncher = true },
        isAdd = isAdd,
        phone = phone,
        password = password,
        swipeToRefresh = {
            if(id != null && id != "-1"){
                notificationsViewModel.getNotification(
                    phone = appViewModel.phoneValue.value,
                    password = appViewModel.passwordValue.value,
                    id = id
                )
            }
        },
        swipeEnabled = id != null && id != "-1"
    )
}

@Composable
fun NotificationScreenContentManager(
    isLoading: Boolean,
    image: Bitmap?,
    getNotificationResponse: Resource<GetNotificationResponse>?,
    appViewModel: AppViewModel,
    notificationsViewModel: NotificationsViewModel,
    galleryLauncher: () -> Unit,
    cameraLauncher: () -> Unit,
    isAdd: Boolean,
    phone: String,
    password: String,
    swipeToRefresh: () -> Unit,
    swipeEnabled: Boolean
){
    SwipeRefresh(
        swipeEnabled = swipeEnabled,
        state = rememberSwipeRefreshState(isRefreshing = isLoading),
        onRefresh = swipeToRefresh
    ) {
        if (isLoading || (getNotificationResponse != null && getNotificationResponse?.data?.message != "Notification Got Successfully!")){
            NotificationScreenContentShimmerAnimated()
        }else {
            NotificationScreenContent(
                image = image,
                getNotificationResponse = getNotificationResponse,
                appViewModel = appViewModel,
                notificationsViewModel = notificationsViewModel,
                galleryLauncher = galleryLauncher,
                cameraLauncher = cameraLauncher,
                isAdd = isAdd,
                phone = phone,
                password = password
            )
        }
    }
}

@Composable
fun NotificationScreenContentShimmerAnimated(){
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
    NotificationScreenShimmerContent(alpha = alphaAnimate)
}

@Composable
fun NotificationScreenShimmerContent(alpha: Float){
    val scrollState = rememberScrollState()
    Column(
        Modifier
            .fillMaxSize()
            .padding(MEDIUM_PADDING)
            .verticalScroll(state = scrollState)
    ) {
        Image(
            modifier = Modifier
                .alpha(alpha = alpha)
                .fillMaxWidth()
                .height(300.dp)
                .clip(RoundedCornerShape(MEDIUM_PADDING)),
            painter = painterResource(id = R.drawable.ic_place_holder),
            contentDescription = "Notification Image",
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(MEDIUM_PADDING))
        repeat(4){
            if (it == 3){
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.small)
                        .height(NOTIFICATION_SCREEN_DESCRIPTION_HEIGHT),
                    shape = RoundedCornerShape(MEDIUM_PADDING)
                ){
                    Box(
                        modifier = Modifier
                            .alpha(alpha = alpha)
                            .fillMaxSize()
                            .background(Color.LightGray)
                    )
                }
            }else{
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.small)
                        .height(DROP_DOWN_HEIGHT),
                    shape = RoundedCornerShape(MEDIUM_PADDING)
                ){
                    Box(
                        modifier = Modifier
                            .alpha(alpha = alpha)
                            .fillMaxSize()
                            .background(Color.LightGray)
                    )
                }
            }
            Spacer(modifier = Modifier.height(MEDIUM_PADDING))
        }
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(60.dp))
                .height(46.dp),
            shape = RoundedCornerShape(MEDIUM_PADDING)
        ){
            Box(
                modifier = Modifier
                    .alpha(alpha = alpha)
                    .fillMaxSize()
                    .background(Color.DarkGray.copy(alpha = alpha))
            )
        }
        Spacer(modifier = Modifier.height(MEDIUM_PADDING))
    }
}

@OptIn(ExperimentalCoilApi::class, ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreenContent(
    image: Bitmap?,
    getNotificationResponse: Resource<GetNotificationResponse>?,
    appViewModel: AppViewModel,
    notificationsViewModel: NotificationsViewModel,
    galleryLauncher: () -> Unit,
    cameraLauncher: () -> Unit,
    isAdd: Boolean,
    phone: String,
    password: String
){
    val scrollState = rememberScrollState()
    var painterImage: Painter
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(vertical = MEDIUM_PADDING)
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(MEDIUM_PADDING)
        ){
            if (image != null){
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(NOTIFICATION_SCREEN_IMAGE_HEIGHT)
                        .clip(RoundedCornerShape(MEDIUM_PADDING)),
                    bitmap = image.asImageBitmap(),
                    contentDescription = "Notification Image",
                    contentScale = ContentScale.Crop
                )
            }else {
                painterImage = if (
                    getNotificationResponse?.data?.notification?.image != null &&
                    getNotificationResponse.data.notification.image != ""
                ){
                    if (isAdd){
                        painterResource(id = R.drawable.ic_place_holder)
                    }else{
                        rememberImagePainter(
                            BASE_URL + appViewModel.baseInfoValue.value?.data?.baseInfo?.uploadUrl
                                    + getNotificationResponse.data.notification.image)
                    }
                } else{
                    painterResource(id = R.drawable.ic_place_holder)
                }
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .clip(RoundedCornerShape(MEDIUM_PADDING)),
                    painter = painterImage,
                    contentDescription = "Notification Image",
                    contentScale = ContentScale.Crop
                )
            }
            Row(modifier = Modifier
                .padding(bottom = MEDIUM_PADDING)
                .align(Alignment.BottomStart)){
                Spacer(modifier = Modifier.width(SMALL_PADDING))
                IconButton(
                    modifier = Modifier.clip(RoundedCornerShape(18.dp)),
                    onClick = { galleryLauncher() },
                    colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_gallery),
                        contentDescription = "Gallery Icon",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
                IconButton(
                    modifier = Modifier.clip(RoundedCornerShape(18.dp)),
                    onClick = {
                        cameraLauncher()
                    },
                    colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_camera),
                        contentDescription = "Gallery Icon",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(MEDIUM_PADDING))

        StatusDropDown(
            modifier = Modifier.padding(horizontal = MEDIUM_PADDING),
            item = notificationsViewModel.notificationRequest.value.status.toStateValue(),
            onItemSelected = { notificationsViewModel.notificationFieldUpdate(status = it)}
        )

        Spacer(modifier = Modifier.height(MEDIUM_PADDING))

        BasesDropDown(
            modifier = Modifier.padding(horizontal = MEDIUM_PADDING),
            items = appViewModel.bases.value!!,
            item = notificationsViewModel.notificationRequest.value.baseId.toBase(appViewModel.bases.value),
            onItemSelected = { notificationsViewModel.notificationFieldUpdate(baseId = it) }
        )

        OutlinedTextField(
            modifier = Modifier
                .padding(horizontal = MEDIUM_PADDING)
                .fillMaxWidth(),
            value = notificationsViewModel.notificationRequest.value.title,
            onValueChange = { notificationsViewModel.notificationFieldUpdate(title = it) },
            textStyle = TextStyle(fontSize = MaterialTheme.typography.titleSmall.fontSize),
            label = { Text(text = "عنوان") }
        )

        OutlinedTextField(
            modifier = Modifier
                .padding(horizontal = MEDIUM_PADDING)
                .fillMaxWidth()
                .height(NOTIFICATION_SCREEN_DESCRIPTION_HEIGHT),
            value = notificationsViewModel.notificationRequest.value.description,
            onValueChange = { notificationsViewModel.notificationFieldUpdate(description = it) },
            textStyle = TextStyle(fontSize = MaterialTheme.typography.titleSmall.fontSize),
            label = { Text(text = "توضیحات") }
        )

        Spacer(modifier = Modifier.height(MEDIUM_PADDING))

        TimeDropDown(
            modifier = Modifier.padding(horizontal = MEDIUM_PADDING),
            item = notificationsViewModel.notificationRequest.value.maxShowTime.toTimeValue(),
            onItemSelected = { notificationsViewModel.notificationFieldUpdate(maxShowTime = it) }
        )

        Spacer(modifier = Modifier.height(MEDIUM_PADDING))

        Button(
            modifier = Modifier
                .padding(horizontal = MEDIUM_PADDING)
                .fillMaxWidth(),
            onClick = {
                if (notificationsViewModel.notificationId.value == "-1"){
                    notificationsViewModel.addNotification(phone = phone, password = password)
                } else{
                    if (image == null){
                        notificationsViewModel.updateNotification(
                            notificationsViewModel.notificationId.value, imageNotUpdate = true,
                            phone = phone,
                            password = password
                        )
                    }else {
                        notificationsViewModel.updateNotification(
                            notificationsViewModel.notificationId.value, imageNotUpdate = false,
                            phone = phone,
                            password = password
                        )
                    }
                }
            }
        ) {
            Text(text = "انتشار اعلان")
        }
    }
}