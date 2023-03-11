package com.tinyDeveloper.na.ui.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import com.tinyDeveloper.na.ui.theme.DROP_DOWN_BORDER_WIDTH
import com.tinyDeveloper.na.ui.theme.DROP_DOWN_HEIGHT
import com.tinyDeveloper.na.ui.theme.DropDownColor
import com.tinyDeveloper.na.ui.theme.MEDIUM_PADDING
import com.tinyDeveloper.na.utils.AdvanceStateValues
import com.tinyDeveloper.na.utils.Constants.HALF_ALPHA
import com.tinyDeveloper.na.utils.StateValues
import com.tinyDeveloper.na.utils.UsersStatusValues

@Composable
fun StatusDropDown(
    modifier: Modifier = Modifier,
    item: StateValues = StateValues.ACTIVE,
    advanceItem: AdvanceStateValues = AdvanceStateValues.ACTIVE,
    usersStatusValuesItem: UsersStatusValues = UsersStatusValues.ACTIVE,
    onItemSelected: (String) -> Unit,
    enabled: Boolean = true,
    isBasic: Boolean = true,
    isUsers: Boolean = false
){
    var expended by remember { mutableStateOf(false) }
    val rotateAnimate by animateFloatAsState(targetValue = if (expended) -180f else 0f)
    var parentSize by remember { mutableStateOf(IntSize.Zero) }

    Box(modifier = modifier){
        Row(
            modifier = Modifier
                .alpha( if (enabled) 1f else HALF_ALPHA )
                .clickable { if (enabled) expended = true }
                .onGloballyPositioned { parentSize = it.size }
                .border(
                    width = DROP_DOWN_BORDER_WIDTH,
                    color = DropDownColor,
                    shape = MaterialTheme.shapes.extraSmall
                )
                .height(DROP_DOWN_HEIGHT),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isBasic){
                Text(
                    modifier = Modifier.weight(0.9f).padding(start = MEDIUM_PADDING),
                    text = item.title,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize
                )
            }else {
                if (isUsers){
                    Text(
                        modifier = Modifier.weight(0.9f).padding(start = MEDIUM_PADDING),
                        text = usersStatusValuesItem.title,
                        fontSize = MaterialTheme.typography.titleMedium.fontSize
                    )
                }else {
                    Text(
                        modifier = Modifier.weight(0.9f).padding(start = MEDIUM_PADDING),
                        text = advanceItem.title,
                        fontSize = MaterialTheme.typography.titleMedium.fontSize
                    )
                }
            }
            IconButton(modifier = Modifier.weight(0.1f),onClick = { expended = true }, enabled = enabled) {
                Icon(
                    modifier = Modifier
                        .rotate(rotateAnimate)
                        .alpha(HALF_ALPHA),
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = "Drop Down Icon"
                )
            }
            DropdownMenu(
                modifier = Modifier.width( with(LocalDensity.current){ parentSize.width.toDp() }),
                expanded = expended,
                onDismissRequest = { expended = false }
            ){
                if (isBasic){
                    StateValues.values().forEach {
                        Text(
                            modifier = Modifier.padding(MEDIUM_PADDING).fillMaxWidth().clickable { onItemSelected(it.value); expended = false },
                            text = it.title,
                            textAlign = TextAlign.Center,
                            fontSize = MaterialTheme.typography.titleSmall.fontSize
                        )
                    }
                }else {
                    if (isUsers){
                        UsersStatusValues.values().forEach {
                            Text(
                                modifier = Modifier.padding(MEDIUM_PADDING).fillMaxWidth().clickable { onItemSelected(it.value); expended = false },
                                text = it.title,
                                textAlign = TextAlign.Center,
                                fontSize = MaterialTheme.typography.titleSmall.fontSize
                            )
                        }
                    }else{
                        AdvanceStateValues.values().forEach {
                            Text(
                                modifier = Modifier.padding(MEDIUM_PADDING).fillMaxWidth().clickable { onItemSelected(it.value); expended = false },
                                text = it.title,
                                textAlign = TextAlign.Center,
                                fontSize = MaterialTheme.typography.titleSmall.fontSize
                            )
                        }
                    }
                }
            }
        }
    }
}