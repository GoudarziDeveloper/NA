package com.tinyDeveloper.na.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.tinyDeveloper.na.ui.screens.AppViewModel
import com.tinyDeveloper.na.ui.screens.splash.SplashScreen
import com.tinyDeveloper.na.utils.Constants.SPLASH_SCREEN

fun NavGraphBuilder.splashComposable(
    navigateToMainScreen: () -> Unit,
    navigateToPhoneScreen: () -> Unit,
    appViewModel: AppViewModel,
    navigateToWelcomeScreen: (isMain: Boolean) -> Unit
) {
    composable(route = SPLASH_SCREEN){
        SplashScreen(
            navigateToMainScreen = navigateToMainScreen,
            navigateToPhoneScreen = navigateToPhoneScreen,
            appViewModel = appViewModel,
            navigateToWelcomeScreen = navigateToWelcomeScreen
        )
    }
}