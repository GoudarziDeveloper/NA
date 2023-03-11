package com.tinyDeveloper.na.ui.screens.main.useers.user

import android.graphics.Bitmap
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.google.accompanist.flowlayout.FlowRow
import com.tinyDeveloper.na.R
import com.tinyDeveloper.na.domain.models.request.users.User
import com.tinyDeveloper.na.domain.models.response.common.Base
import com.tinyDeveloper.na.ui.component.BasesDropDown
import com.tinyDeveloper.na.ui.component.PickImageGallery
import com.tinyDeveloper.na.ui.component.StatusDropDown
import com.tinyDeveloper.na.ui.component.TakePhoto
import com.tinyDeveloper.na.ui.screens.main.useers.UsersViewModel
import com.tinyDeveloper.na.ui.theme.*
import com.tinyDeveloper.na.utils.Constants
import com.tinyDeveloper.na.utils.toBase
import com.tinyDeveloper.na.utils.toStateValue
import com.tinyDeveloper.na.utils.toUsersStatusValue
import io.getstream.chat.android.compose.ui.util.mirrorRtl
import okhttp3.internal.notifyAll

@Composable
fun UserScreenContent(
    usersViewModel: UsersViewModel,
    baseUrl: String,
    bases: List<Base>,
    subClicked: (Bitmap?) -> Unit,
    isUpdate: Boolean
){
    val image by usersViewModel.image
    val user by usersViewModel.user
    var painterImage by remember{ mutableStateOf<Painter?>(null) }
    val scrollState = rememberScrollState()

    var galleryLaunch by remember { mutableStateOf(false) }
    PickImageGallery(isLaunch = galleryLaunch, launched = { galleryLaunch = false }) {
        if (it != null) {
            galleryLaunch = false
            usersViewModel.updateImage(it)
        }
    }

    var cameraLauncher by remember { mutableStateOf(false) }
    TakePhoto(
        isLaunch = cameraLauncher,
        launched = { cameraLauncher = false },
        onBitmapReady = { bitmap: Bitmap?, success: Boolean ->
            if (success && bitmap != null){
                usersViewModel.updateImage(bitmap)
            }
        }
    )

    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(state = scrollState)
        .padding(MEDIUM_PADDING),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(EXTRA_LARGE_PADDING))
        Surface(modifier = Modifier.size(PROFILE_IMAGE_HEIGHT), shape = RoundedCornerShape(
            LARGEST_PADDING
        )
        ) {
            Box(modifier = Modifier.fillMaxSize()){
                if (image != null){
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        bitmap = image!!.asImageBitmap(),
                        contentDescription = "Profile Image",
                        contentScale = ContentScale.Crop
                    )
                }else {
                    painterImage = if (user.image.isNotEmpty()){
                        if (isUpdate){
                            rememberImagePainter(baseUrl + user.image)
                        }
                        else
                            painterResource(id = R.drawable.ic_user)
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
                    .background(Color.DarkGray.copy(alpha = Constants.HALF_ALPHA))
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

        Row(modifier = Modifier.fillMaxWidth()) {
            UserScreenItem(
                modifier = Modifier.weight(0.5f),
                label = "نام",
                value = user.firstName,
                onValueChange = { usersViewModel.updateUser(firstName = it) }
            )
            Spacer(modifier = Modifier.width(MEDIUM_PADDING))
            UserScreenItem(
                modifier = Modifier.weight(0.5f),
                label = "نام خانوادگی",
                value = user.lastName,
                onValueChange = { usersViewModel.updateUser(lastName = it) }
            )
        }

        Row(modifier = Modifier.fillMaxWidth()) {
            UserScreenItem(
                modifier = Modifier.weight(0.5f),
                label = "نام پدر",
                value = user.fatherName,
                onValueChange = { usersViewModel.updateUser(fatherName = it) }
            )
            Spacer(modifier = Modifier.width(MEDIUM_PADDING))
            BasesDropDown(
                modifier = Modifier
                    .weight(0.5f)
                    .padding(top = MEDIUM_PADDING),
                items = bases,
                item = user.baseId.toBase(bases = bases),
                onItemSelected = { usersViewModel.updateUser(baseId = it) }
            )
        }

        UserScreenItem(
            modifier = Modifier.fillMaxWidth(),
            label = "شماره موبایل",
            value = user.phone,
            onValueChange = { usersViewModel.updateUser(phone = it) },
            centerText = true,
            enabled = !isUpdate
        )

        UserScreenItem(
            modifier = Modifier.fillMaxWidth(),
            label = "رمز ورود",
            value = user.password,
            onValueChange = { usersViewModel.updateUser(password = it) },
            centerText = true
        )

        UserScreenItem(
            modifier = Modifier.fillMaxWidth(),
            label = "کد ملی",
            value = user.nationalCode,
            onValueChange = { usersViewModel.updateUser(nationalCode = it) },
            centerText = true
        )


        UserScreenItem(
            modifier = Modifier.fillMaxWidth(),
            label = "تاریخ تولد",
            value = user.birthDay,
            onValueChange = { usersViewModel.updateUser(birthDay = it) },
            centerText = true
        )

        StatusDropDown(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = MEDIUM_PADDING),
            usersStatusValuesItem = user.status.toUsersStatusValue(),
            onItemSelected = { usersViewModel.updateUser(status = it)},
            isUsers = true,
            isBasic = false
        )

        Spacer(modifier = Modifier.height(MEDIUM_PADDING))

        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(MEDIUM_PADDING)) {
            Text(
                text = "سطح دسترسی کاربر",
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(SMALL_PADDING))
            FlowRow(
                modifier = Modifier.fillMaxWidth().mirrorRtl(LayoutDirection.Rtl),
                mainAxisSpacing = MEDIUM_PADDING,
                crossAxisSpacing = MEDIUM_PADDING,
            ) {
                UserRoleItem(
                    checked = usersViewModel.roles.value.addUser.checked,
                    onCheckedChange = { usersViewModel.updateRoles(addUser = it) },
                    label = usersViewModel.roles.value.addUser.title
                )
                UserRoleItem(
                    checked = usersViewModel.roles.value.updateUser.checked,
                    onCheckedChange = { usersViewModel.updateRoles(updateUser = it) },
                    label = usersViewModel.roles.value.updateUser.title
                )
                UserRoleItem(
                    checked = usersViewModel.roles.value.deleteUser.checked,
                    onCheckedChange = { usersViewModel.updateRoles(deleteUser = it) },
                    label = usersViewModel.roles.value.deleteUser.title
                )
                UserRoleItem(
                    checked = usersViewModel.roles.value.updateUsers.checked,
                    onCheckedChange = { usersViewModel.updateRoles(updateUsers = it) },
                    label = usersViewModel.roles.value.updateUsers.title
                )
                UserRoleItem(
                    checked = usersViewModel.roles.value.getUser.checked,
                    onCheckedChange = { usersViewModel.updateRoles(getUser = it) },
                    label = usersViewModel.roles.value.getUser.title
                )
                UserRoleItem(
                    checked = usersViewModel.roles.value.getUsers.checked,
                    onCheckedChange = { usersViewModel.updateRoles(getUsers = it) },
                    label = usersViewModel.roles.value.getUsers.title
                )
                UserRoleItem(
                    checked = usersViewModel.roles.value.getNotifications.checked,
                    onCheckedChange = { usersViewModel.updateRoles(getNotifications = it) },
                    label = usersViewModel.roles.value.getNotifications.title
                )
                UserRoleItem(
                    checked = usersViewModel.roles.value.addNotification.checked,
                    onCheckedChange = { usersViewModel.updateRoles(addNotification = it) },
                    label = usersViewModel.roles.value.addNotification.title
                )
                UserRoleItem(
                    checked = usersViewModel.roles.value.deleteNotification.checked,
                    onCheckedChange = { usersViewModel.updateRoles(deleteNotification = it) },
                    label = usersViewModel.roles.value.deleteNotification.title
                )
                UserRoleItem(
                    checked = usersViewModel.roles.value.updateNotification.checked,
                    onCheckedChange = { usersViewModel.updateRoles(updateNotification = it) },
                    label = usersViewModel.roles.value.updateNotification.title
                )
                UserRoleItem(
                    checked = usersViewModel.roles.value.addJob.checked,
                    onCheckedChange = { usersViewModel.updateRoles(addJob = it) },
                    label = usersViewModel.roles.value.addJob.title
                )
                UserRoleItem(
                    checked = usersViewModel.roles.value.deleteJob.checked,
                    onCheckedChange = { usersViewModel.updateRoles(deleteJob = it) },
                    label = usersViewModel.roles.value.deleteJob.title
                )
                UserRoleItem(
                    checked = usersViewModel.roles.value.updateJob.checked,
                    onCheckedChange = { usersViewModel.updateRoles(updateJob = it) },
                    label = usersViewModel.roles.value.updateJob.title
                )
                UserRoleItem(
                    checked = usersViewModel.roles.value.getJob.checked,
                    onCheckedChange = { usersViewModel.updateRoles(getJob = it) },
                    label = usersViewModel.roles.value.getJob.title
                )
                UserRoleItem(
                    checked = usersViewModel.roles.value.getFile.checked,
                    onCheckedChange = { usersViewModel.updateRoles(getFile = it) },
                    label = usersViewModel.roles.value.getFile.title
                )
                UserRoleItem(
                    checked = usersViewModel.roles.value.addFile.checked,
                    onCheckedChange = { usersViewModel.updateRoles(addFile = it) },
                    label = usersViewModel.roles.value.addFile.title
                )
                UserRoleItem(
                    checked = usersViewModel.roles.value.deleteFile.checked,
                    onCheckedChange = { usersViewModel.updateRoles(deleteFile = it) },
                    label = usersViewModel.roles.value.deleteFile.title
                )
                UserRoleItem(
                    checked = usersViewModel.roles.value.addBase.checked,
                    onCheckedChange = { usersViewModel.updateRoles(addBase = it) },
                    label = usersViewModel.roles.value.addBase.title
                )
                UserRoleItem(
                    checked = usersViewModel.roles.value.deleteBase.checked,
                    onCheckedChange = { usersViewModel.updateRoles(deleteBase = it) },
                    label = usersViewModel.roles.value.deleteBase.title
                )
                UserRoleItem(
                    checked = usersViewModel.roles.value.updateBase.checked,
                    onCheckedChange = { usersViewModel.updateRoles(updateBase = it) },
                    label = usersViewModel.roles.value.updateBase.title
                )
                UserRoleItem(
                    checked = usersViewModel.roles.value.getBases.checked,
                    onCheckedChange = { usersViewModel.updateRoles(getBases = it) },
                    label = usersViewModel.roles.value.getBases.title
                )
                UserRoleItem(
                    checked = usersViewModel.roles.value.addSubmission.checked,
                    onCheckedChange = { usersViewModel.updateRoles(addSubmission = it) },
                    label = usersViewModel.roles.value.addSubmission.title
                )
                UserRoleItem(
                    checked = usersViewModel.roles.value.deleteSubmission.checked,
                    onCheckedChange = { usersViewModel.updateRoles(deleteSubmission = it) },
                    label = usersViewModel.roles.value.deleteSubmission.title
                )
                UserRoleItem(
                    checked = usersViewModel.roles.value.updateSubmission.checked,
                    onCheckedChange = { usersViewModel.updateRoles(updateSubmission = it) },
                    label = usersViewModel.roles.value.updateSubmission.title
                )
                UserRoleItem(
                    checked = usersViewModel.roles.value.getSubmissions.checked,
                    onCheckedChange = { usersViewModel.updateRoles(getSubmissions = it) },
                    label = usersViewModel.roles.value.getSubmissions.title
                )
                UserRoleItem(
                    checked = usersViewModel.roles.value.getBaseInfo.checked,
                    onCheckedChange = { usersViewModel.updateRoles(getBaseInfo = it) },
                    label = usersViewModel.roles.value.getBaseInfo.title
                )
                UserRoleItem(
                    checked = usersViewModel.roles.value.editBaseInfo.checked,
                    onCheckedChange = { usersViewModel.updateRoles(editBaseInfo = it) },
                    label = usersViewModel.roles.value.editBaseInfo.title
                )
            }
            Spacer(modifier = Modifier.height(MEDIUM_PADDING))
            androidx.compose.material3.Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { subClicked(image) }
            ) {
                Text(
                    text = "ثبت اطلاعات کاربر",
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
            Spacer(modifier = Modifier.height(MEDIUM_PADDING))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserScreenItem(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    onValueChange:(String) -> Unit,
    centerText: Boolean = false,
    enabled: Boolean = true
){
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        enabled = enabled,
        textStyle = if (centerText){
            LocalTextStyle.current.copy(
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                textAlign = TextAlign.Center)
        } else {
            LocalTextStyle.current.copy(
                fontSize = MaterialTheme.typography.titleMedium.fontSize)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserRoleItem(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    label: String
) {
    Row(
        modifier = Modifier
            .border(color = BorderColor, width = 1.dp, shape = RoundedCornerShape(MEDIUM_PADDING))
            .background(CardColor)
            .clickable { onCheckedChange(!checked) }
            .padding(start = MEDIUM_PADDING)
            .mirrorRtl(LayoutDirection.Rtl),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
        Text(text = label)
    }
}