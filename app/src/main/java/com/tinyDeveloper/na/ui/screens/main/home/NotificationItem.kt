package com.tinyDeveloper.na.ui.screens.main.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.tinyDeveloper.na.domain.models.response.notifications.Notification
import com.tinyDeveloper.na.ui.theme.MEDIUM_PADDING
import com.tinyDeveloper.na.ui.theme.NOTIFICATION_ITEM_HEIGHT
import com.tinyDeveloper.na.ui.theme.SMALL_PADDING
import com.tinyDeveloper.na.utils.Constants.BASE_URL
import com.tinyDeveloper.na.utils.Constants.HALF_ALPHA
import com.tinyDeveloper.na.utils.toTime

@OptIn(ExperimentalMaterial3Api::class, ExperimentalCoilApi::class)
@Composable
fun NotificationItem(
    uploadUrl: String,
    notification: Notification,
    onItemClicked: (id:Int) -> Unit,
    update: Boolean,
    delete: Boolean,
    deleteClicked: (String) -> Unit,
    updateClicked: (String) -> Unit
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(if (notification.image.isNotEmpty()) NOTIFICATION_ITEM_HEIGHT else 160.dp)
            .padding(MEDIUM_PADDING),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp, hoveredElevation = 0.dp),
        onClick = { onItemClicked(notification.id.toInt()) }
    ) {
        Box(modifier = Modifier.fillMaxSize()){
            if (notification.image.isNotEmpty()){
                val imagePainter = rememberImagePainter("$BASE_URL/$uploadUrl/${notification.image}")
                Image(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.TopCenter),
                    painter = imagePainter,
                    contentDescription = "Notification Item Image",
                    contentScale = ContentScale.Crop
                )
                Surface(modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .align(Alignment.BottomCenter),
                    color = Color.DarkGray.copy(alpha = HALF_ALPHA)
                ){}
            }
            Column(modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(160.dp)
                .padding(MEDIUM_PADDING),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start
            ) {
                Row(modifier = Modifier.fillMaxWidth()){
                    Text(
                        modifier = Modifier.weight(8f),
                        text = notification.title,
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        color = if (notification.image.isNotEmpty()) Color.White else MaterialTheme.colorScheme.onSurface
                    )
                    if (update){
                        IconButton(
                            modifier = Modifier.size(28.dp),
                            onClick = { updateClicked(notification.id) }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit Icon",
                                tint =
                                if(notification.image.isEmpty())
                                    MaterialTheme.colorScheme.onSurface
                                else
                                    Color.White
                            )
                        }
                    }
                    if (delete){
                        Spacer(modifier = Modifier.width(SMALL_PADDING))
                        IconButton(
                            modifier = Modifier.size(28.dp),
                            onClick = { deleteClicked(notification.id) }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Edit Icon",
                                tint =
                                if(notification.image.isEmpty())
                                    MaterialTheme.colorScheme.onSurface
                                else
                                    Color.White
                            )
                        }
                    }
                }
                Text(
                    text = notification.date.toTime(),
                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    color = if (notification.image.isNotEmpty()) Color.White else MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(MEDIUM_PADDING))
                Text(
                    text = notification.description,
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 3,
                    color = if (notification.image.isNotEmpty()) Color.White else MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}