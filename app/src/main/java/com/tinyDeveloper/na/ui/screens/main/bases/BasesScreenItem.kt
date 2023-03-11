package com.tinyDeveloper.na.ui.screens.main.bases

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.tinyDeveloper.na.domain.models.response.bases.get_all.Base
import com.tinyDeveloper.na.ui.theme.MEDIUM_PADDING
import com.tinyDeveloper.na.ui.theme.SMALL_PADDING
import com.tinyDeveloper.na.utils.setStatus
import com.tinyDeveloper.na.utils.toColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasesScreenItem(
    base: Base,
    onItemClicked: (String) -> Unit,
    onDeleteClicked: (String) -> Unit
){
    Card(modifier = Modifier
        .fillMaxWidth(), onClick = { onItemClicked(base.id) }, elevation = CardDefaults.cardElevation(
        defaultElevation = 4.dp,
        pressedElevation = 0.dp,
        hoveredElevation = 0.dp)
    ) {
        Box(modifier = Modifier
            .fillMaxSize()){
            Box(modifier = Modifier
                .clip(RoundedCornerShape(topStart = MEDIUM_PADDING))
                .background(base.status.toColor())
                .padding(SMALL_PADDING)
                .align(Alignment.BottomEnd)
            ) {
                Text(
                    modifier = Modifier
                        .padding(horizontal = SMALL_PADDING),
                    text = " وضعیت: ${base.status.setStatus()}",
                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.White
                )
            }
            Column(modifier = Modifier.fillMaxWidth().padding(MEDIUM_PADDING), horizontalAlignment = Alignment.Start) {
                Text(
                    text = base.baseName,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(SMALL_PADDING))
                Text(
                    modifier = Modifier.fillMaxWidth(0.7f),
                    text = base.address,
                    fontSize = MaterialTheme.typography.titleSmall.fontSize,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            IconButton(onClick = { onDeleteClicked(base.id)},  modifier = Modifier
                .padding(MEDIUM_PADDING)
                .size(24.dp)
                .align(Alignment.TopEnd) ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Base Icon"
                )
            }
        }
    }
}