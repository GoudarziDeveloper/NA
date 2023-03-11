package com.tinyDeveloper.na.ui.component

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.tinyDeveloper.na.R

@Composable
fun DisplayAlertDialog(
    openDialog: Boolean,
    title: String,
    description: String,
    onCloseClicked: () -> Unit,
    onYesClicked: () -> Unit,
    yesTitle: String,
    noTitle:String,
    dismiss: Boolean = true,
    onDismissRequest: () -> Unit = {}
){
    if (openDialog) {
        AlertDialog(
            onDismissRequest = if (dismiss) onCloseClicked else onDismissRequest,
            title = {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            },
            confirmButton = {
                Button(onClick = onYesClicked) {
                    Text(text = yesTitle)
                }
            },
            dismissButton = {
                if(noTitle.isNotEmpty()){
                    OutlinedButton(onClick = onCloseClicked) {
                        Text(text = noTitle)
                    }
                }
            }
        )
    }
}