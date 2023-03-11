package com.tinyDeveloper.na.ui.screens.main.settings

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tinyDeveloper.na.ui.component.ButtonShimmer
import com.tinyDeveloper.na.ui.component.TextFieldShimmer
import com.tinyDeveloper.na.ui.component.TextShimmer
import com.tinyDeveloper.na.ui.component.rememberAnimatedFloat
import com.tinyDeveloper.na.ui.theme.MEDIUM_PADDING
import com.tinyDeveloper.na.ui.theme.SHIMMER_EXTRA_SMALL
import com.tinyDeveloper.na.ui.theme.ShimmerColor

@Composable
fun SettingsScreenShimmer(
    scrollState: ScrollState
){
    val alpha = rememberAnimatedFloat()
    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(scrollState)
        .padding(MEDIUM_PADDING)
    ) {
        SettingsScreenShimmerDivider(alpha = alpha)
        Spacer(modifier = Modifier.height(MEDIUM_PADDING))
        SettingsScreenShimmerCheckBox(alpha = alpha)
        Spacer(modifier = Modifier.height(MEDIUM_PADDING))
        repeat(2){
            TextFieldShimmer(modifier = Modifier.fillMaxWidth(), alpha = alpha)
            Spacer(modifier = Modifier.height(MEDIUM_PADDING))
        }
        Spacer(modifier = Modifier.height(MEDIUM_PADDING))
        Spacer(modifier = Modifier.height(MEDIUM_PADDING))
        Spacer(modifier = Modifier.height(MEDIUM_PADDING))
        SettingsScreenShimmerDivider(alpha = alpha)
        Spacer(modifier = Modifier.height(MEDIUM_PADDING))
        SettingsScreenShimmerCheckBox(alpha = alpha)
        Spacer(modifier = Modifier.height(MEDIUM_PADDING))
        Row(modifier = Modifier.fillMaxWidth()){
            repeat(3){
                TextFieldShimmer(modifier = Modifier.weight(1f), alpha = alpha)
                if(it < 2)
                    Spacer(modifier = Modifier.width(MEDIUM_PADDING))
            }
        }
        Spacer(modifier = Modifier.height(MEDIUM_PADDING))
        Spacer(modifier = Modifier.height(MEDIUM_PADDING))
        Spacer(modifier = Modifier.height(MEDIUM_PADDING))
        SettingsScreenShimmerDivider(alpha = alpha)
        Spacer(modifier = Modifier.height(MEDIUM_PADDING))
        Spacer(modifier = Modifier.height(MEDIUM_PADDING))
        Row(modifier = Modifier.fillMaxWidth()) {
            TextFieldShimmer(modifier = Modifier.weight(1f), alpha = alpha)
            Spacer(modifier = Modifier.width(MEDIUM_PADDING))
            TextFieldShimmer(modifier = Modifier.weight(1f), alpha = alpha)
        }
        Spacer(modifier = Modifier.height(MEDIUM_PADDING))
        repeat(3){
            TextFieldShimmer(modifier = Modifier.fillMaxWidth(), alpha = alpha)
            Spacer(modifier = Modifier.height(MEDIUM_PADDING))
        }
        Spacer(modifier = Modifier.height(MEDIUM_PADDING))
        Spacer(modifier = Modifier.height(MEDIUM_PADDING))
        Spacer(modifier = Modifier.height(MEDIUM_PADDING))
        SettingsScreenShimmerDivider(alpha = alpha)
        Spacer(modifier = Modifier.height(MEDIUM_PADDING))
        SettingsScreenShimmerCheckBox(alpha = alpha)
        Spacer(modifier = Modifier.height(MEDIUM_PADDING))
        repeat(3){
            TextFieldShimmer(modifier = Modifier.fillMaxWidth(), alpha = alpha)
            Spacer(modifier = Modifier.height(MEDIUM_PADDING))
        }
        ButtonShimmer(modifier = Modifier.fillMaxWidth(), alpha = alpha)
        Spacer(modifier = Modifier.height(MEDIUM_PADDING))
    }
}

@Composable
fun SettingsScreenShimmerDivider(alpha: Float){
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        TextShimmer(
            modifier = Modifier.fillMaxWidth(0.2f),
            alpha = alpha,
            height = SHIMMER_EXTRA_SMALL
        )
        Spacer(modifier = Modifier.width(MEDIUM_PADDING))
        Divider(modifier = Modifier.height(1.dp), color = ShimmerColor)
    }
}

@Composable
fun SettingsScreenShimmerCheckBox(alpha: Float){
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(MEDIUM_PADDING),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextShimmer(
            modifier = Modifier.size(24.dp),
            alpha = alpha,
            shape = RoundedCornerShape(2.dp),
            height = 24.dp
        )
        Spacer(modifier = Modifier.width(MEDIUM_PADDING))
        TextShimmer(modifier = Modifier.fillMaxWidth(0.5f), alpha = alpha)
    }
}