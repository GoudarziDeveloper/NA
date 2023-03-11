package com.tinyDeveloper.na.navigation.destinations.main.destinations

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.runtime.*
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.tinyDeveloper.na.ui.screens.AppViewModel
import com.tinyDeveloper.na.ui.screens.main.bases.BasesScreen
import com.tinyDeveloper.na.ui.screens.main.bases.BasesViewModel
import com.tinyDeveloper.na.utils.Constants
import com.tinyDeveloper.na.utils.Constants.BASE_SCREEN

fun NavGraphBuilder.basesComposable(
    appViewModel: AppViewModel,
    adminStatus: (Boolean) -> Unit,
    fabNavigate: (String) -> Unit,
    screenTitle: (String) -> Unit,
    navigateToBaseScreen: (String) -> Unit,
    basesViewModel: BasesViewModel){
    composable(route = Constants.BASES_SCREEN){
        var isAppeared by remember { mutableStateOf(false) }
        LaunchedEffect(key1 = Unit) {
            isAppeared = true
            screenTitle(Constants.BASES_SCREEN_TITLE)
            fabNavigate("$BASE_SCREEN/-1")
        }
        AnimatedVisibility(
            visible = isAppeared,
            enter = fadeIn(
                animationSpec = tween(
                    durationMillis = Constants.SCREENS_DURATION
                )
            )
        ) {
            BasesScreen(appViewModel = appViewModel, adminStatus = adminStatus, navigateToBaseScreen = navigateToBaseScreen, basesViewModel = basesViewModel)
        }
    }
}