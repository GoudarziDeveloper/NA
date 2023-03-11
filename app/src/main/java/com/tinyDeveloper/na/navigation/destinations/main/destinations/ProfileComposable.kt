package com.tinyDeveloper.na.navigation.destinations.main.destinations

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.runtime.*
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.tinyDeveloper.na.ui.screens.AppViewModel
import com.tinyDeveloper.na.ui.screens.main.profile.ProfileScreen
import com.tinyDeveloper.na.ui.screens.main.profile.ProfileViewModel
import com.tinyDeveloper.na.ui.screens.main.useers.UsersViewModel
import com.tinyDeveloper.na.utils.Constants
import com.tinyDeveloper.na.utils.Constants.PROFILE_SCREEN_TITLE

fun NavGraphBuilder.profileComposable(
    appViewModel: AppViewModel,
    adminStatus: (Boolean) -> Unit,
    screenTitle: (String) -> Unit,
    profileViewModel: ProfileViewModel,
    usersViewModel: UsersViewModel,
){
    composable(route = Constants.PROFILE_SCREEN){
        var isAppeared by remember { mutableStateOf(false) }
        LaunchedEffect(key1 = Unit){
            screenTitle(PROFILE_SCREEN_TITLE)
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
            ProfileScreen(
                appViewModel = appViewModel,
                profileViewModel = profileViewModel,
                usersViewModel = usersViewModel
            )
        }
    }
}