package com.tinyDeveloper.na.ui.screens.main.settings

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import com.tinyDeveloper.na.ui.theme.MEDIUM_PADDING
import com.tinyDeveloper.na.utils.Constants.HALF_ALPHA

@Composable
fun SettingsScreenContent(
    settingsViewModel: SettingsViewModel,
    scrollState: ScrollState
){
    val baseInfo = settingsViewModel.updateBaseInfoRequest.value

    Column(modifier = Modifier
        .verticalScroll(state = scrollState)
        .fillMaxSize()
        .padding(MEDIUM_PADDING)) {
        SettingsScreenDivider(label = "سامانه")
        Row(modifier = Modifier
            .fillMaxWidth()
            .clickable {
                if (baseInfo?.appEnabled == "1") settingsViewModel.updateFields(appEnabled = "0")
                else settingsViewModel.updateFields(appEnabled = "1")
            }, verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = baseInfo?.appEnabled == "1",
                onCheckedChange = {
                    if (baseInfo?.appEnabled == "1") settingsViewModel.updateFields(appEnabled = "0")
                    else settingsViewModel.updateFields(appEnabled = "1")
                }
            )
            Text(text = "فعال بودن سامانه", fontSize = MaterialTheme.typography.titleSmall.fontSize)
        }
        SettingsScreenItem(
            modifier = Modifier.fillMaxWidth(),
            label = "عنوان پیام توقف",
            value = baseInfo?.stopTitle?:"",
            onValueChange = { settingsViewModel.updateFields(stopTitle = it) },
            enabled = baseInfo?.appEnabled != "1"
        )
        SettingsScreenItem(
            modifier = Modifier.fillMaxWidth(),
            label = "متن پیام توقف",
            value = baseInfo?.stopText?:"",
            onValueChange = { settingsViewModel.updateFields(stopText = it) },
            enabled = baseInfo?.appEnabled != "1"
        )
        Spacer(modifier = Modifier.height(MEDIUM_PADDING))
        Spacer(modifier = Modifier.height(MEDIUM_PADDING))
        Spacer(modifier = Modifier.height(MEDIUM_PADDING))
        SettingsScreenDivider(label = "محیط")
        Row(modifier = Modifier
            .fillMaxWidth()
            .clickable {
                if (baseInfo?.isSnow == "1") settingsViewModel.updateFields(isSnow = "0")
                else settingsViewModel.updateFields(isSnow = "1")
            }, verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = baseInfo?.isSnow == "1",
                onCheckedChange = {
                    if (baseInfo?.isSnow == "1") settingsViewModel.updateFields(isSnow = "0")
                    else settingsViewModel.updateFields(isSnow = "1")
                }
            )
            Text(text = "بارش برف", fontSize = MaterialTheme.typography.titleSmall.fontSize)
        }
        Row(modifier = Modifier
            .fillMaxWidth()) {
            SettingsScreenItem(
                modifier = Modifier.weight(1f),
                label = "نام مجموعه",
                value = baseInfo?.center ?: "",
                onValueChange = { settingsViewModel.updateFields(center = it) },
                enabled = true
            )
            Spacer(modifier = Modifier.width(MEDIUM_PADDING))
            SettingsScreenItem(
                modifier = Modifier.weight(1f),
                label = "نام جزء",
                value = baseInfo?.part ?: "",
                onValueChange = { settingsViewModel.updateFields(part = it) },
                enabled = true
            )
            Spacer(modifier = Modifier.width(MEDIUM_PADDING))
            SettingsScreenItem(
                modifier = Modifier.weight(1f),
                label = "شخص اول",
                value = baseInfo?.manager ?: "",
                onValueChange = { settingsViewModel.updateFields(manager = it) },
                enabled = true
            )
        }
        Spacer(modifier = Modifier.height(MEDIUM_PADDING))
        Spacer(modifier = Modifier.height(MEDIUM_PADDING))
        Spacer(modifier = Modifier.height(MEDIUM_PADDING))
        SettingsScreenDivider(label = "بروزرسانی")
        Spacer(modifier = Modifier.height(MEDIUM_PADDING))
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            SettingsScreenItem(
                modifier = Modifier.weight(1f),
                label = "قدیمی ترین ورژن",
                value = baseInfo?.minVersion?:"",
                onValueChange = { if(it.isDigitsOnly()) settingsViewModel.updateFields(minVersion = it) },
                enabled = true
            )
            Spacer(modifier = Modifier.width(MEDIUM_PADDING))
            SettingsScreenItem(
                modifier = Modifier.weight(1f),
                label = "جدید ترین ورژن",
                value = baseInfo?.lastVersion ?: "",
                onValueChange = { if (it.isDigitsOnly()) settingsViewModel.updateFields(lastVersion = it) },
                enabled = true
            )
        }
        SettingsScreenItem(
            modifier = Modifier.fillMaxWidth(),
            label = "عنوان پیام بروز رسانی",
            value = baseInfo?.updateTitle ?: "",
            onValueChange = { settingsViewModel.updateFields(updateTitle = it) },
            enabled = true
        )
        SettingsScreenItem(
            modifier = Modifier.fillMaxWidth(),
            label = "متن پیام بروز رسانی",
            value = baseInfo?.updateText?:"",
            onValueChange = { settingsViewModel.updateFields(updateText = it) },
            enabled = true
        )
        SettingsScreenItem(
            modifier = Modifier.fillMaxWidth(),
            label = "لینک بروز رسانی",
            value = baseInfo?.updateLink ?: "",
            onValueChange = { settingsViewModel.updateFields(updateLink = it) },
            enabled = true
        )
        Spacer(modifier = Modifier.height(MEDIUM_PADDING))
        Spacer(modifier = Modifier.height(MEDIUM_PADDING))
        Spacer(modifier = Modifier.height(MEDIUM_PADDING))
        SettingsScreenDivider(label = "خوش امد گویی")
        Spacer(modifier = Modifier.height(MEDIUM_PADDING))
        Row(modifier = Modifier
            .fillMaxWidth()
            .clickable {
                if (baseInfo?.messageEnabled == "1") {
                    settingsViewModel.updateFields(messageEnabled = "0")
                } else {
                    settingsViewModel.updateFields(messageEnabled = "1")
                }
            }, verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = baseInfo?.messageEnabled == "1",
                onCheckedChange = {
                    if (baseInfo?.messageEnabled == "1"){
                        settingsViewModel.updateFields(messageEnabled = "0")
                    } else{
                        settingsViewModel.updateFields(messageEnabled = "1")
                    }
                }
            )
            Text(text = "متن خوش امد گویی", fontSize = MaterialTheme.typography.titleSmall.fontSize)
        }
        SettingsScreenItem(
            modifier = Modifier.fillMaxWidth(),
            label = "عنوان پیام خوش امد گویی",
            value = baseInfo?.messageTitle ?: "",
            onValueChange = { settingsViewModel.updateFields(messageTitle = it) },
            enabled = baseInfo?.messageEnabled == "1"
        )
        SettingsScreenItem(
            modifier = Modifier.fillMaxWidth(),
            label = "متن پیام خوش امد گویی",
            value = baseInfo?.messageText?:"",
            onValueChange = { settingsViewModel.updateFields(messageText = it) },
            enabled = baseInfo?.messageEnabled == "1"
        )
        SettingsScreenItem(
            modifier = Modifier.fillMaxWidth(),
            label = "لینک اپلودها",
            value = baseInfo?.uploadUrl ?: "",
            onValueChange = { settingsViewModel.updateFields(uploadUrl = it) },
            enabled = false
        )
        Button(modifier = Modifier.fillMaxWidth(), onClick = { settingsViewModel.updateBaseInfo() }) {
            Text(text = "ثبت اطلاعات", fontSize = MaterialTheme.typography.titleSmall.fontSize, fontWeight = FontWeight.Bold)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreenItem(
    modifier: Modifier,
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean
){
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        textStyle = LocalTextStyle.current.copy(
            fontSize = MaterialTheme.typography.titleMedium.fontSize
        ),
        singleLine = true,
        enabled = enabled
    )
}

@Composable
fun SettingsScreenDivider(label: String){
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){
        Text(text = label)
        Spacer(modifier = Modifier.width(MEDIUM_PADDING))
        Divider(modifier = Modifier.height(1.dp), color = Color.DarkGray.copy(alpha = HALF_ALPHA))
    }
}