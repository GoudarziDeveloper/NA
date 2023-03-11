package com.tinyDeveloper.na.navigation.destinations.main.destinations

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.runtime.*
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.tinyDeveloper.na.ui.screens.AppViewModel
import com.tinyDeveloper.na.ui.screens.main.settings.SettingsScreen
import com.tinyDeveloper.na.ui.screens.main.settings.SettingsViewModel
import com.tinyDeveloper.na.utils.Constants

fun NavGraphBuilder.settingsComposable(
    appViewModel: AppViewModel,
    screenTitle: (String) -> Unit,
    settingsViewModel: SettingsViewModel){
    composable(route = Constants.SETTINGS_SCREEN){
        var isAppeared by remember { mutableStateOf(false) }
        LaunchedEffect(key1 = Unit){
            screenTitle(Constants.SETTINGS_SCREEN_TITLE)
            isAppeared = true
        }
        AnimatedVisibility(
            visible = isAppeared,
            enter = fadeIn(
                animationSpec = tween(
                    durationMillis = Constants.SCREENS_DURATION
                )
            )
        ) {
            SettingsScreen(appViewModel = appViewModel, settingsViewModel = settingsViewModel)
        }
    }
}