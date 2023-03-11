package com.tinyDeveloper.na.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.tinyDeveloper.na.ui.theme.MEDIUM_PADDING

@Composable
fun EmptyScreen(
    image: Int,
    text: String,
    isButton: Boolean = false,
    buttonTitle: String = "",
    onClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Surface {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    modifier = Modifier.fillMaxWidth(0.6f),
                    painter = painterResource(id = image),
                    contentDescription = "Empty Screen Image"
                )
                Spacer(modifier = Modifier.height(MEDIUM_PADDING))
                Text(
                    text = text,
                    modifier = Modifier.fillMaxWidth(0.8f),
                    fontSize = MaterialTheme.typography.titleSmall.fontSize,
                    textAlign = TextAlign.Center
                )
                if (isButton){
                    Spacer(modifier = Modifier.height(MEDIUM_PADDING))
                    Button(onClick = onClick, modifier = Modifier.fillMaxWidth(0.8f)) {
                        Text(text = buttonTitle)
                    }
                }
            }
        }
    }
}