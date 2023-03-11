package com.tinyDeveloper.na.navigation.destinations.main.destinations

import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.tinyDeveloper.na.ui.screens.AppViewModel
import com.tinyDeveloper.na.ui.screens.main.home.NotificationsViewModel
import com.tinyDeveloper.na.ui.screens.main.home.notification.NotificationDetailsScreen
import com.tinyDeveloper.na.utils.Constants
import com.tinyDeveloper.na.utils.Constants.NOTIFICATION_DETAILS_TITLE

fun NavGraphBuilder.notificationDetailsComposable(
    appViewModel: AppViewModel,
    adminStatus: (Boolean) -> Unit,
    screenTitle: (String) -> Unit,
    notificationsViewModel: NotificationsViewModel
){
    composable(
        route = Constants.NOTIFICATION_DETAILS_SCREEN+"/{id}",
        arguments = listOf(navArgument("id"){ type = NavType.StringType })
    ){ navBackEntry ->
        LaunchedEffect(key1 = Unit){
            screenTitle(NOTIFICATION_DETAILS_TITLE)
        }
        val id: String? = navBackEntry.arguments?.getString("id")
        NotificationDetailsScreen(
            appViewModel = appViewModel,
            id = id,
            notificationsViewModel = notificationsViewModel
        )
    }
}