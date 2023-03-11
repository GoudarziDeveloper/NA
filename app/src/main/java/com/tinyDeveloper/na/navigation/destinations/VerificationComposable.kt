package com.tinyDeveloper.na.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.tinyDeveloper.na.ui.screens.AppViewModel
import com.tinyDeveloper.na.ui.screens.login.verification.VerificationScreen
import com.tinyDeveloper.na.utils.Constants.VERIFICATION_SCREEN

fun NavGraphBuilder.verificationComposable(
    navigateToMainScreen: () -> Unit,
    navigateToPhoneScreen: () -> Unit,
    appViewModel: AppViewModel
){
    composable(route = VERIFICATION_SCREEN){
        VerificationScreen(
            navigateToPhoneScreen = { navigateToPhoneScreen(); appViewModel.logout() },
            navigateToMainScreen = navigateToMainScreen,
            appViewModel = appViewModel
        )
    }
}