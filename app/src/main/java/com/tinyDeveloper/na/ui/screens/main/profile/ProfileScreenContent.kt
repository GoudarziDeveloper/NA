package com.tinyDeveloper.na.ui.screens.main.profile

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.tinyDeveloper.na.R
import com.tinyDeveloper.na.domain.models.response.common.Base
import com.tinyDeveloper.na.domain.models.response.users.get_profile.User
import com.tinyDeveloper.na.ui.component.PickImageGallery
import com.tinyDeveloper.na.ui.component.TakePhoto
import com.tinyDeveloper.na.ui.theme.EXTRA_LARGE_PADDING
import com.tinyDeveloper.na.ui.theme.LARGEST_PADDING
import com.tinyDeveloper.na.ui.theme.MEDIUM_PADDING
import com.tinyDeveloper.na.ui.theme.PROFILE_IMAGE_HEIGHT
import com.tinyDeveloper.na.utils.Constants.HALF_ALPHA
import com.tinyDeveloper.na.utils.Constants.MANAGER_NAME
import com.tinyDeveloper.na.utils.Constants.PART_NAME

@Composable
fun SetProfileScreenContent(
    image: Bitmap?,
    isLoading: Boolean,
    editMode: Boolean,
    user: User?,
    base: Base?,
    baseUrl: String,
    updateImage: (Bitmap?) -> Unit,
    swipeToRefresh: () -> Unit,
    exitClicked: () -> Unit,
    editClicked: () -> Unit
){
    SwipeRefresh(state = rememberSwipeRefreshState(isRefreshing = isLoading), onRefresh = swipeToRefresh) {
        if (!isLoading && user != null && base != null){
            ProfileScreenContent(
                image = image,
                editMode = editMode,
                user = user,
                base = base,
                baseUrl = baseUrl,
                updateImage = updateImage,
                editClicked = editClicked,
                exitClicked = exitClicked
            )
        }else {
            ProfileScreenShimmer()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreenContent(
    image: Bitmap?,
    editMode: Boolean,
    user: User,
    base: Base,
    baseUrl: String,
    updateImage: (Bitmap?) -> Unit,
    editClicked: () -> Unit,
    exitClicked: () -> Unit
){
    val scrollState = rememberScrollState()
    var painterImage by remember { mutableStateOf<Painter?>(null) }
    var galleryLaunch by remember { mutableStateOf(false) }
    PickImageGallery(isLaunch = galleryLaunch, launched = { galleryLaunch = false }) {
        if (it != null) {
            galleryLaunch = false
            updateImage(it)
        }
    }

    var cameraLauncher by remember { mutableStateOf(false) }
    TakePhoto(
        isLaunch = cameraLauncher,
        launched = { cameraLauncher = false },
        onBitmapReady = { bitmap: Bitmap?, success: Boolean ->
            if (success && bitmap != null){
                updateImage(bitmap)
            }
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(modifier = Modifier.height(EXTRA_LARGE_PADDING))
        Surface(modifier = Modifier.size(PROFILE_IMAGE_HEIGHT), shape = RoundedCornerShape(LARGEST_PADDING)) {
            Box(modifier = Modifier.fillMaxSize()){
                if (image != null){
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        bitmap = image.asImageBitmap(),
                        contentDescription = "Profile Image",
                        contentScale = ContentScale.Crop
                    )
                }else {
                    painterImage = if (user.image != ""){
                        rememberAsyncImagePainter(baseUrl + user.image)
                    } else{
                        painterResource(id = R.drawable.ic_user)
                    }
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = painterImage!!,
                        contentDescription = "Profile Image",
                        contentScale = ContentScale.Crop
                    )
                }
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .height(45.dp)
                    .background(Color.DarkGray.copy(alpha = HALF_ALPHA))
                ){
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                        IconButton(
                            modifier = Modifier.weight(1f),
                            onClick = { cameraLauncher = true }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_camera),
                                contentDescription = "Camera Icon",
                                tint = Color.White
                            )
                        }
                        IconButton(
                            modifier = Modifier.weight(1f),
                            onClick = { galleryLaunch = true }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_gallery),
                                contentDescription = "Gallery Icon",
                                tint = Color.White
                            )
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(MEDIUM_PADDING))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MEDIUM_PADDING),
            value = user.phone,
            onValueChange = { },
            enabled = editMode,
            label = { Text(text = "شماره موبایل") },
            textStyle = LocalTextStyle.current.copy(
                textAlign = TextAlign.Center,
                fontSize = MaterialTheme.typography.titleMedium.fontSize
            ),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(MEDIUM_PADDING))
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = MEDIUM_PADDING)) {
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = user.firstName,
                onValueChange = {},
                enabled = editMode,
                label = { Text(text = "نام") },
                textStyle = LocalTextStyle.current.copy(
                    fontSize = MaterialTheme.typography.titleMedium.fontSize
                ),
                singleLine = true
            )
            Spacer(modifier = Modifier.width(MEDIUM_PADDING))
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = user.lastName,
                onValueChange = {},
                enabled = editMode,
                label = { Text(text = "نام خانوادگی") },
                textStyle = LocalTextStyle.current.copy(
                    fontSize = MaterialTheme.typography.titleMedium.fontSize
                ),
                singleLine = true
            )
        }
        Spacer(modifier = Modifier.height(MEDIUM_PADDING))
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = MEDIUM_PADDING)) {
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = user.fatherName,
                onValueChange = { },
                enabled = editMode,
                label = { Text(text = "نام پدر") },
                textStyle = LocalTextStyle.current.copy(
                    fontSize = MaterialTheme.typography.titleMedium.fontSize
                ),
                singleLine = true
            )
            Spacer(modifier = Modifier.width(MEDIUM_PADDING))
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = user.nationalCode,
                onValueChange = { },
                enabled = editMode,
                label = { Text(text = "کد ملی") },
                textStyle = LocalTextStyle.current.copy(
                    fontSize = MaterialTheme.typography.titleMedium.fontSize
                ),
                singleLine = true
            )
        }
        Spacer(modifier = Modifier.height(MEDIUM_PADDING))
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = MEDIUM_PADDING)) {
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = user.birthDay,
                onValueChange = { },
                enabled = editMode,
                label = { Text(text = "تاریخ تولد") },
                textStyle = LocalTextStyle.current.copy(
                    fontSize = MaterialTheme.typography.titleMedium.fontSize
                ),
                singleLine = true
            )
            Spacer(modifier = Modifier.width(MEDIUM_PADDING))
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = base.baseName,
                onValueChange = { },
                enabled = editMode,
                label = { Text(text = "نام $PART_NAME") },
                textStyle = LocalTextStyle.current.copy(
                    fontSize = MaterialTheme.typography.titleSmall.fontSize
                ),
                singleLine = true
            )
        }
        Spacer(modifier = Modifier.height(MEDIUM_PADDING))
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = MEDIUM_PADDING)) {
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = base.commanderName,
                onValueChange = { },
                enabled = editMode,
                label = { Text(text = "نام $MANAGER_NAME") },
                textStyle = LocalTextStyle.current.copy(
                    fontSize = androidx.compose.material3.MaterialTheme.typography.titleMedium.fontSize
                ),
                singleLine = true
            )
            Spacer(modifier = Modifier.width(MEDIUM_PADDING))
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = base.commanderPhone,
                onValueChange = { },
                enabled = editMode,
                label = { Text(text = "شماره $MANAGER_NAME $PART_NAME") },
                textStyle = LocalTextStyle.current.copy(
                    fontSize = androidx.compose.material3.MaterialTheme.typography.titleMedium.fontSize
                ),
                singleLine = true
            )
        }
        Spacer(modifier = Modifier.height(MEDIUM_PADDING))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MEDIUM_PADDING),
            value = base.address,
            onValueChange = { },
            enabled = editMode,
            label = { Text(text = "ادرس $PART_NAME") },
            textStyle = LocalTextStyle.current.copy(
                fontSize = androidx.compose.material3.MaterialTheme.typography.titleMedium.fontSize
            )
        )
        Spacer(modifier = Modifier.height(MEDIUM_PADDING))
        Row(modifier = Modifier.fillMaxWidth().padding(MEDIUM_PADDING)) {
            Button(modifier = Modifier
                .weight(1f),
                onClick = editClicked) {
                Text(
                    text = "ویرایش",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize
                )
            }
            Spacer(modifier = Modifier.width(MEDIUM_PADDING))
            Button(
                modifier = Modifier
                    .weight(1f),
                onClick = exitClicked,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.errorContainer)
            ) {
                Text(
                    text = "خروج از حساب",
                    color = MaterialTheme.colorScheme.onErrorContainer,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize
                )
            }
        }
        Spacer(modifier = Modifier.height(MEDIUM_PADDING))
    }
}