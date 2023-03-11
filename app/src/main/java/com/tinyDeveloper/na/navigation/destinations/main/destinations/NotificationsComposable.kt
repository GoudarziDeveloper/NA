package com.tinyDeveloper.na.navigation.destinations.main.destinations

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.runtime.*
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.tinyDeveloper.na.ui.screens.AppViewModel
import com.tinyDeveloper.na.ui.screens.main.home.chat.ChatViewModel
import com.tinyDeveloper.na.ui.screens.main.home.NotificationsScreen
import com.tinyDeveloper.na.ui.screens.main.home.NotificationsViewModel
import com.tinyDeveloper.na.utils.Constants
import com.tinyDeveloper.na.utils.Constants.NOTIFICATIONS_SCREEN_TITLE
import com.tinyDeveloper.na.utils.Constants.NOTIFICATION_SCREEN
import io.getstream.chat.android.compose.viewmodel.messages.AttachmentsPickerViewModel
import io.getstream.chat.android.compose.viewmodel.messages.MessageComposerViewModel
import io.getstream.chat.android.compose.viewmodel.messages.MessageListViewModel

fun NavGraphBuilder.notificationsComposable(
    appViewModel: AppViewModel,
    navigateToNotificationScreen: (id: Int)-> Unit,
    adminStatus: (Boolean) -> Unit,
    screenTitle: (String) -> Unit,
    fabNavigate: (route: String) -> Unit,
    navigateToUpdate: (String) -> Unit,
    notificationsViewModel: NotificationsViewModel,
    chatViewModel: ChatViewModel,
){
    composable(route = Constants.NOTIFICATIONS_SCREEN){
        var isAppeared by remember { mutableStateOf(false) }
        LaunchedEffect(key1 = Unit){
            isAppeared = true
            fabNavigate("$NOTIFICATION_SCREEN/-1")
            screenTitle(NOTIFICATIONS_SCREEN_TITLE)
        }
        AnimatedVisibility(
            visible = isAppeared,
            enter = fadeIn(
                animationSpec = tween(
                    durationMillis = Constants.SCREENS_DURATION
                )
            )
        ) {
            NotificationsScreen(
                appViewModel = appViewModel,
                navigateToNotificationScreen = navigateToNotificationScreen,
                adminStatus = adminStatus,
                navigateToUpdate = navigateToUpdate,
                notificationsViewModel = notificationsViewModel,
                chatViewModel = chatViewModel,
            )
        }
    }
}