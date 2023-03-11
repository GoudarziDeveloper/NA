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
import androidx.compose.ui.unit.dp
import com.tinyDeveloper.na.domain.models.response.common.Base
import com.tinyDeveloper.na.ui.theme.DROP_DOWN_BORDER_WIDTH
import com.tinyDeveloper.na.ui.theme.DROP_DOWN_HEIGHT
import com.tinyDeveloper.na.ui.theme.DropDownColor
import com.tinyDeveloper.na.ui.theme.MEDIUM_PADDING
import com.tinyDeveloper.na.utils.Constants.HALF_ALPHA

@Composable
fun BasesDropDown(
    modifier: Modifier = Modifier,
    items: List<Base>,
    item: Base,
    onItemSelected: (String) -> Unit
){
    var expended by remember { mutableStateOf(false) }

    val rotateAnimate by animateFloatAsState(targetValue = if (expended) -180f else 0f)
    var parentSize by remember { mutableStateOf(IntSize.Zero) }

    Box(modifier = modifier){
        Row(
            modifier = Modifier
                .clickable { expended = true }
                .onGloballyPositioned { parentSize = it.size }
                .border(
                    width = DROP_DOWN_BORDER_WIDTH,
                    color = DropDownColor,
                    shape = MaterialTheme.shapes.extraSmall
                )
                .height(DROP_DOWN_HEIGHT),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(0.9f).padding(start = MEDIUM_PADDING),
                text = item.baseName,
                fontSize = androidx.compose.material3.MaterialTheme.typography.titleSmall.fontSize
            )
            IconButton(modifier = Modifier.weight(0.1f),onClick = { expended = true }) {
                Icon(
                    modifier = Modifier
                        .rotate(rotateAnimate)
                        .alpha(HALF_ALPHA),
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = "Drop Down Icon"
                )
            }
            DropdownMenu(
                modifier = Modifier.width( with(LocalDensity.current){ parentSize.width.toDp() }).height(300.dp),
                expanded = expended,
                onDismissRequest = { expended = false }
            ){
                items.forEach {
                    Text(
                        modifier = Modifier.padding(MEDIUM_PADDING).fillMaxWidth().clickable { onItemSelected(it.id); expended = false },
                        text = it.baseName,
                        textAlign = TextAlign.Center,
                        fontSize = androidx.compose.material3.MaterialTheme.typography.titleSmall.fontSize
                    )
                }
            }
        }
    }
}