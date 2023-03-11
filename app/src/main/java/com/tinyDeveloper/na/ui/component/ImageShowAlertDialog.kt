package com.tinyDeveloper.na.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.tinyDeveloper.na.ui.theme.MEDIUM_PADDING

@Composable
fun ImageShowAlertDialog(isOpen: Boolean, imagePainter: Painter, onDismiss: ()-> Unit){
    if (isOpen){
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text(text = "تصویر کاربر")
            },
            text = {
                Image(modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp), painter = imagePainter,
                    contentDescription = "Image Show Alert Dialog", contentScale = ContentScale.Crop)
            },
            confirmButton = {
                Button(onClick = onDismiss){
                    Text(text = "باشه")
                }
            }
        )
    }
}