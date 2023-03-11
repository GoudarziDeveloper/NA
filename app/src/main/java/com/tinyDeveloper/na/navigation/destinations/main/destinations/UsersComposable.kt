package com.tinyDeveloper.na.navigation.destinations.main.destinations

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.runtime.*
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.tinyDeveloper.na.ui.screens.AppViewModel
import com.tinyDeveloper.na.ui.screens.main.useers.UsersScreen
import com.tinyDeveloper.na.ui.screens.main.useers.UsersViewModel
import com.tinyDeveloper.na.utils.Constants.SCREENS_DURATION
import com.tinyDeveloper.na.utils.Constants.USERS_SCREEN
import com.tinyDeveloper.na.utils.Constants.USERS_SCREEN_TITLE
import com.tinyDeveloper.na.utils.Constants.USER_SCREEN

fun NavGraphBuilder.usersComposable(
    appViewModel: AppViewModel,
    adminStatus: (Boolean) -> Unit,
    screenTitle: (String) -> Unit,
    navigateToUserScreen: (String) -> Unit,
    fabNavigate: (String) -> Unit,
    usersViewModel: UsersViewModel,
    navigateToPersonalChat: () -> Unit
){
    composable(route = USERS_SCREEN){
        var isAppeared by remember { mutableStateOf(false) }
        LaunchedEffect(key1 = Unit){
            isAppeared = true
            screenTitle(USERS_SCREEN_TITLE)
            fabNavigate("$USER_SCREEN/-1")
        }
        AnimatedVisibility(
            visible = isAppeared,
            enter = fadeIn(
                animationSpec = tween(
                    durationMillis = SCREENS_DURATION
                )
            )
        ) {
            UsersScreen(
                appViewModel = appViewModel,
                navigateToUserScreen = navigateToUserScreen,
                adminStatus = adminStatus,
                usersViewModel = usersViewModel,
                navigateToPersonalChat = navigateToPersonalChat
            )
        }
    }
}