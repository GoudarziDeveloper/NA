package com.tinyDeveloper.na.ui.component

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.util.Base64
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext


@Composable
fun SelectFile(
    isLaunch: Boolean,
    launched: () -> Unit,
    onReady: (String?) -> Unit
){
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ){uri: Uri? ->
        uri?.let {
            onReady(toBase64(uri, context = context))
        }
    }
    if (isLaunch){
        launcher.launch("*/*")
        launched()
    }
}


@SuppressLint("Recycle")
fun toBase64(uri: Uri, context: Context): String{
    val bytes = context.contentResolver.openInputStream(uri)?.readBytes()
    return "data:;base64,${Base64.encodeToString(bytes, Base64.DEFAULT)}"
}