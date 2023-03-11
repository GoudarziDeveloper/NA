package com.tinyDeveloper.na.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.tinyDeveloper.na.R
import com.tinyDeveloper.na.domain.models.response.files.File
import com.tinyDeveloper.na.ui.theme.*
import com.tinyDeveloper.na.utils.toTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FileItem(
    file: File,
    jobTitle: String?,
    itemClicked: (String) -> Unit,
    deleteFileClicked: (String) -> Unit,
    deleteFile: Boolean
){
    var name = file.src.split("//")[1]
    val fullName = name.split('.')
    val baseName = "erena_${fullName[0]}"
    val extension = fullName[1]
    name = "$baseName.$extension"
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(LARGE_PADDING),
        elevation = CardDefaults.cardElevation(defaultElevation = SMALL_PADDING, pressedElevation = 0.dp, hoveredElevation = 0.dp),
        onClick = { itemClicked(file.src) }
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(MEDIUM_PADDING)){
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Surface(modifier = Modifier
                    .size(48.dp),
                    shape = CircleShape,
                    color = NotShimmerColor
                ){
                    Icon(
                        modifier = Modifier.padding(SMALL_PADDING),
                        painter = painterResource(id = R.drawable.ic_file),
                        contentDescription = "File Icon",
                        tint = ShimmerColor
                    )
                }
                Spacer(modifier = Modifier.width(MEDIUM_PADDING))
                Text(
                    text = name.ifEmpty { jobTitle ?: "فایل اپلود شده" },
                    fontSize = MaterialTheme.typography.titleSmall.fontSize,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
            if (deleteFile){
                IconButton(
                    onClick = { deleteFileClicked(file.id) },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .size(24.dp)
                ) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Delete File Icon")
                }
            }
            Text(
                modifier = Modifier.align(Alignment.BottomEnd),
                text = file.date.toTime(),
                fontSize = MaterialTheme.typography.labelSmall.fontSize
            )
        }
    }
}