package com.tinyDeveloper.na.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.tinyDeveloper.na.R
import com.tinyDeveloper.na.ui.theme.LARGE_PADDING
import com.tinyDeveloper.na.ui.theme.MEDIUM_PADDING
import com.tinyDeveloper.na.ui.theme.NumberPadBackButton
import com.tinyDeveloper.na.utils.Constants.HALF_ALPHA

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NumberPad(
    modifier: Modifier,
    phone: String,
    onPhoneChange: (String) -> Unit,
    acceptEnable: Boolean,
    acceptClicked: () -> Unit
){
    var row = 0
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(topStart = MEDIUM_PADDING, topEnd = LARGE_PADDING))
            .background(color = MaterialTheme.colorScheme.surfaceVariant)
            .padding(MEDIUM_PADDING),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        repeat(4){
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly){
                repeat(3){
                    row++
                    val item = (when(row){ 1 -> 3 3 -> 1 4-> 6 6->4 7->9 9->7 10->"!" 12->">" 11->0 else -> row })
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .padding(MEDIUM_PADDING)
                            .height(58.dp),
                        onClick = {
                            if(item != ">" && item != "!")
                                onPhoneChange(phone+item)
                            else if (item == ">") onPhoneChange("$item")
                            else if( item == "!"){ if (acceptEnable){ acceptClicked() } }
                        },
                        elevation =
                            if (item == "!" && !acceptEnable) CardDefaults.cardElevation(defaultElevation = 0.dp)
                            else CardDefaults.cardElevation(defaultElevation = 8.dp, hoveredElevation = 0.dp, pressedElevation = 0.dp),
                        colors = CardDefaults.cardColors(containerColor =
                            when (item) {
                                ">" -> NumberPadBackButton
                                "!" -> MaterialTheme.colorScheme.primary
                                else -> MaterialTheme.colorScheme.surface
                            }
                        )
                    ) {
                        Box(modifier = Modifier.fillMaxSize()){
                            if(item != ">" && item != "!"){
                                Text(
                                    text = "$item",
                                    textAlign = TextAlign.Center,
                                    color = MaterialTheme.colorScheme.onSurface.copy(HALF_ALPHA),
                                    fontSize = MaterialTheme.typography.displaySmall.fontSize,
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            } else if (item == ">") {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_cleat_text), contentDescription = "Number Pad Delete Icon",
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                        .size(36.dp),
                                    tint = MaterialTheme.colorScheme.onPrimary.copy(HALF_ALPHA)
                                )
                            } else if (item == "!"){
                                Icon(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .align(Alignment.Center),
                                    imageVector = Icons.Filled.Check, contentDescription = "Number Pad Check Icon",
                                    tint = MaterialTheme.colorScheme.onPrimary.copy(HALF_ALPHA)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}