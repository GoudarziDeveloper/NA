package com.tinyDeveloper.na.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.tinyDeveloper.na.ui.screens.AppViewModel
import com.tinyDeveloper.na.ui.screens.login.phone.PhoneScreen
import com.tinyDeveloper.na.utils.Constants.PHONE_SCREEN

fun NavGraphBuilder.phoneComposable(
    navigateToPasswordScreen: () -> Unit,
    appViewModel: AppViewModel
){
    composable(route = PHONE_SCREEN){
        PhoneScreen(navigateToPasswordScreen = navigateToPasswordScreen, appViewModel = appViewModel)
    }
}