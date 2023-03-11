package com.tinyDeveloper.na.ui.screens.main.useers

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.tinyDeveloper.na.R
import com.tinyDeveloper.na.domain.models.response.users.get_all.User
import com.tinyDeveloper.na.ui.component.EmptyScreen
import com.tinyDeveloper.na.ui.component.ImageShowAlertDialog
import com.tinyDeveloper.na.ui.theme.MEDIUM_PADDING
import com.tinyDeveloper.na.ui.theme.SMALL_PADDING
import com.tinyDeveloper.na.utils.*

@Composable
fun UsersScreenContent(
    users: List<User>?,
    baseUrl: String,
    isLoader: Boolean,
    deleteEnabled: Boolean,
    deleteClicked: (phone: String, status: String) -> Unit,
    navigateToUserScreen: (String) -> Unit,
    swipeToRefresh: () -> Unit
){
    SwipeRefresh(state = rememberSwipeRefreshState(isRefreshing = isLoader), onRefresh = swipeToRefresh) {
        if (users != null && !isLoader){
            if (users.isNotEmpty()){
                var imageShowIsOpen by remember{ mutableStateOf(false) }
                var imagePainter by remember { mutableStateOf<Painter?>(null) }
                if (imagePainter != null){
                    ImageShowAlertDialog(isOpen = imageShowIsOpen, imagePainter = imagePainter!!) {
                        imageShowIsOpen = false
                    }
                }
                val maxScore = users.max()
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(all = MEDIUM_PADDING),
                    verticalArrangement = Arrangement.spacedBy(MEDIUM_PADDING)
                ){
                    items(users){
                        UsersItem(
                            user = it,
                            baseUrl = baseUrl,
                            deleteEnabled = deleteEnabled,
                            deleteClicked = deleteClicked,
                            navigateToUserScreen = navigateToUserScreen,
                            maxScore = maxScore,
                            openUserImage = { image -> imageShowIsOpen = true; imagePainter = image }
                        )
                    }
                }
            }else {
                EmptyScreen(
                    image = R.drawable.ic_empty_screen2,
                    text = "در حال حاضر هیچ کاربری ثبت نشده است با دکمه + کاربر جدیدی اضافه کنید."
                )
            }
        }else {
            UsersScreenShimmer()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersItem(
    user: User,
    baseUrl: String,
    deleteEnabled: Boolean,
    deleteClicked: (phone: String, status: String) -> Unit,
    navigateToUserScreen: (String) -> Unit,
    maxScore: Int,
    openUserImage: (image: Painter) -> Unit
){
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp, hoveredElevation = 0.dp),
        onClick = { navigateToUserScreen(user.phone) }
    ) {
        Box(modifier = Modifier.fillMaxWidth()){
            Box(modifier = Modifier
                .clip(RoundedCornerShape(topStart = MEDIUM_PADDING))
                .background(user.status.toColor())
                .padding(SMALL_PADDING)
                .align(Alignment.BottomEnd)
            ) {
                Text(
                    modifier = Modifier
                        .padding(horizontal = SMALL_PADDING),
                    text = user.status.setStatus(),
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.White
                )
            }
            if (user.status == UsersStatusValues.MAN_POWER.value){
                Box(modifier = Modifier
                    .clip(RoundedCornerShape(topStart = MEDIUM_PADDING, topEnd = MEDIUM_PADDING))
                    .background(
                        user.score
                            .toString()
                            .toSubmissionScoreColor(max = maxScore)
                    )
                    .padding(SMALL_PADDING)
                    .align(Alignment.BottomCenter)
                ) {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = SMALL_PADDING),
                        text = " امتیاز: ${user.score}",
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.White
                    )
                }
            }
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(MEDIUM_PADDING)
            ) {
                val imagePainter = remember{ mutableStateOf<Painter?>(null) }
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) {
                    if (user.image.isNotEmpty()){
                        imagePainter.value = rememberImagePainter(baseUrl + user.image)
                    }else{
                        imagePainter.value = painterResource(id = R.drawable.ic_user)
                    }
                    Image(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .clickable { openUserImage(imagePainter.value!!) },
                        painter = imagePainter.value!!,
                        contentDescription = "Users Item Image",
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(MEDIUM_PADDING))
                    Column {
                        Text(
                            modifier = Modifier
                                .padding(horizontal = SMALL_PADDING)
                                .fillMaxWidth(),
                            text = "${user.firstName} ${user.lastName}",
                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                            overflow = TextOverflow.Ellipsis,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            modifier = Modifier
                                .padding(horizontal = SMALL_PADDING)
                                .fillMaxWidth(),
                            text = user.baseName,
                            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
            if (deleteEnabled){
                IconButton(onClick = { deleteClicked(user.phone, user.status) }, modifier = Modifier
                    .size(48.dp)
                    .align(Alignment.TopEnd)) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete User Icon"
                    )
                }
            }
        }
    }
}