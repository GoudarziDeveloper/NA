package com.tinyDeveloper.na.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.tinyDeveloper.na.ui.screens.AppViewModel
import com.tinyDeveloper.na.ui.screens.login.password.PasswordScreen
import com.tinyDeveloper.na.utils.Constants.PASSWORD_SCREEN

fun NavGraphBuilder.passwordComposable(
    navigateToVerificationScreen: () -> Unit,
    navigateToPhoneScreen: () -> Unit,
    appViewModel: AppViewModel
){
    composable(route = PASSWORD_SCREEN){
        PasswordScreen(
            navigateToVerificationScreen = navigateToVerificationScreen,
            navigateToPhoneScreen = { navigateToPhoneScreen(); appViewModel.logout() },
            appViewModel = appViewModel
        )
    }
}