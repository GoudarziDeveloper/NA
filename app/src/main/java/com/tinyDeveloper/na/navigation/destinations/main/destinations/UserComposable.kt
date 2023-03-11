package com.tinyDeveloper.na.navigation.destinations.main.destinations

import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.tinyDeveloper.na.ui.screens.AppViewModel
import com.tinyDeveloper.na.ui.screens.main.useers.UsersViewModel
import com.tinyDeveloper.na.ui.screens.main.useers.user.UserScreen
import com.tinyDeveloper.na.utils.Constants.USER_SCREEN
import com.tinyDeveloper.na.utils.Constants.USER_SCREEN_ARGUMENT
import com.tinyDeveloper.na.utils.Constants.USER_TITLE

fun NavGraphBuilder.userComposable(
    appViewModel: AppViewModel,
    adminStatus: (Boolean) -> Unit,
    screenTitle: (String) -> Unit,
    navigateToUsersScreen:() -> Unit,
    usersViewModel: UsersViewModel
){
    composable(
        route = "$USER_SCREEN/{$USER_SCREEN_ARGUMENT}",
        arguments = listOf(navArgument(USER_SCREEN_ARGUMENT){ type = NavType.StringType })){
        LaunchedEffect(key1 = Unit){
            screenTitle(USER_TITLE)
        }
        val phone = it.arguments?.getString(USER_SCREEN_ARGUMENT)

        phone?.let{
            UserScreen(
                appViewModel = appViewModel,
                adminStatus = adminStatus,
                phone = phone,
                navigateToUsersScreen = navigateToUsersScreen,
                usersViewModel = usersViewModel
            )
        }
    }
}