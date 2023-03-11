package com.tinyDeveloper.na.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.tinyDeveloper.na.ui.theme.INPUT_CHAR_HEIGHT
import com.tinyDeveloper.na.ui.theme.INPUT_CHAR_WIDTH
import com.tinyDeveloper.na.ui.theme.SMALL_PADDING
import com.tinyDeveloper.na.utils.Constants.HALF_ALPHA

@Composable
fun InputChar(inputChar: String, modifier: Modifier){
    Surface(modifier = modifier
        .width(INPUT_CHAR_WIDTH)
        .height(INPUT_CHAR_HEIGHT)
        .padding(2.dp),
        shape = RoundedCornerShape(SMALL_PADDING),
        color = MaterialTheme.colorScheme.surfaceVariant,
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
            Text(
                text = inputChar,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = HALF_ALPHA),
                textAlign = TextAlign.Center,
                fontStyle = MaterialTheme.typography.displaySmall.fontStyle,
                fontSize = MaterialTheme.typography.displaySmall.fontSize
            )
        }
    }
}