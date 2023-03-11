package com.tinyDeveloper.na.navigation.destinations.main.destinations

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.runtime.*
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.tinyDeveloper.na.ui.screens.AppViewModel
import com.tinyDeveloper.na.ui.screens.main.home.NotificationsViewModel
import com.tinyDeveloper.na.ui.screens.main.home.notification.NotificationScreen
import com.tinyDeveloper.na.utils.Constants
import com.tinyDeveloper.na.utils.Constants.NOTIFICATION_SCREEN
import com.tinyDeveloper.na.utils.Constants.NOTIFICATION_TITLE

fun NavGraphBuilder.notificationComposable(
    appViewModel: AppViewModel,
    adminStatus: (Boolean) -> Unit,
    screenTitle: (String) -> Unit,
    navigateToNotificationsScreen: () -> Unit,
    notificationsViewModel: NotificationsViewModel
){
    composable(
        route = "$NOTIFICATION_SCREEN/{id}",
        arguments = listOf(navArgument("id"){ type = NavType.StringType })
    ){ navBackEntry ->
        LaunchedEffect(key1 = Unit){
            screenTitle(NOTIFICATION_TITLE)
        }
        val id = navBackEntry.arguments?.getString("id")
        NotificationScreen(
            appViewModel = appViewModel,
            navigateToNotificationsScreen = navigateToNotificationsScreen,
            id = id,
            notificationsViewModel = notificationsViewModel
        )
    }
}