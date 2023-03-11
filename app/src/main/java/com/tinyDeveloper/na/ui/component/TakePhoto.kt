package com.tinyDeveloper.na.ui.component

import android.content.Context
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
import androidx.core.content.FileProvider
import java.io.File

class ComposeFileProvider : FileProvider(
    com.tinyDeveloper.na.R.xml.filepaths
) {
    companion object {
        fun getImageUri(context: Context): Uri {
            val directory = File(context.cacheDir, "images")
            directory.mkdirs()
            val file = File.createTempFile(
                "selected_image_",
                ".jpg",
                directory
            )
            val authority = context.packageName + ".na-image"
            return getUriForFile(
                context,
                authority,
                file
            )
        }
    }
}

@Composable
fun TakePhoto(
    isLaunch: Boolean,
    onBitmapReady: (Bitmap?, Boolean) -> Unit,
    launched: () -> Unit
){
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    var hasImage by remember { mutableStateOf(false) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ){ success ->
        hasImage = success
    }

    imageUri?.let {
        if(hasImage){
            bitmap = if (Build.VERSION.SDK_INT < 28){
                MediaStore.Images.Media.getBitmap(context.contentResolver, it)
            } else{
                val source = ImageDecoder.createSource(context.contentResolver, it)
                ImageDecoder.decodeBitmap(source)
            }
            onBitmapReady(bitmap, hasImage)
            imageUri = null
            hasImage = false
        }
    }

    if (isLaunch){
        val uri = ComposeFileProvider.getImageUri(context = context)
        imageUri = uri
        launcher.launch(imageUri)
        launched()
    }
}