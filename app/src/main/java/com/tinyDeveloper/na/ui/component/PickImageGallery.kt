package com.tinyDeveloper.na.ui.component

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext

@Composable
fun PickImageGallery(
    isLaunch: Boolean,
    launched: () -> Unit,
    onBitmapReady: (Bitmap?) -> Unit
){
    var imageUri by remember{ mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    var bitmap by remember{ mutableStateOf<Bitmap?>(null) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ){uri: Uri? ->
        imageUri = uri
    }
    imageUri?.let {
        bitmap = if (Build.VERSION.SDK_INT < 28){
            MediaStore.Images.Media.getBitmap(context.contentResolver, it)
        } else{
            val source = ImageDecoder.createSource(context.contentResolver, it)
            ImageDecoder.decodeBitmap(source)
        }
        onBitmapReady(bitmap)
        imageUri = null
    }
    if (isLaunch){
        launcher.launch("image/*")
        launched()
    }
}