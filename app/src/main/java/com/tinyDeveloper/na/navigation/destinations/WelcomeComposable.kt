package com.tinyDeveloper.na.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.tinyDeveloper.na.ui.screens.welcome.WelcomeScreen
import com.tinyDeveloper.na.utils.Constants.WELCOME_ARGUMENT
import com.tinyDeveloper.na.utils.Constants.WELCOME_SCREEN

fun NavGraphBuilder.welcomeComposable(
    navigateToMainScreen: () -> Unit,
    navigateToPhoneScreen: () -> Unit
){
    composable(
        route = "$WELCOME_SCREEN/{$WELCOME_ARGUMENT}",
        arguments = listOf(navArgument(name = WELCOME_ARGUMENT){ type = NavType.BoolType })
    ){ it ->
        it.arguments?.getBoolean(WELCOME_ARGUMENT)?.let{ isMain ->
            WelcomeScreen(
                navigateToPhoneScreen = navigateToPhoneScreen,
                navigateToMainScreen = navigateToMainScreen,
                isMain = isMain
            )
        }
    }
}
