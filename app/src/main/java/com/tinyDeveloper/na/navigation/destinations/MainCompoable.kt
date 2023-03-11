package com.tinyDeveloper.na.navigation.destinations

import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.tinyDeveloper.na.ui.screens.AppViewModel
import com.tinyDeveloper.na.ui.screens.main.MainScreen
import com.tinyDeveloper.na.ui.screens.main.bases.BasesViewModel
import com.tinyDeveloper.na.ui.screens.main.home.chat.ChatViewModel
import com.tinyDeveloper.na.ui.screens.main.jobs.JobsViewModel
import com.tinyDeveloper.na.ui.screens.main.home.NotificationsViewModel
import com.tinyDeveloper.na.ui.screens.main.profile.ProfileViewModel
import com.tinyDeveloper.na.ui.screens.main.settings.SettingsViewModel
import com.tinyDeveloper.na.ui.screens.main.useers.UsersViewModel
import com.tinyDeveloper.na.utils.Constants.MAIN_SCREEN

fun NavGraphBuilder.mainComposable(
    appViewModel: AppViewModel,
    notificationsViewModel: NotificationsViewModel,
    jobsViewModel: JobsViewModel,
    usersViewModel: UsersViewModel,
    profileViewModel: ProfileViewModel,
    basesViewModel: BasesViewModel,
    settingsViewModel: SettingsViewModel,
    chatViewModel: ChatViewModel,
    showSnow: () -> Unit
){
    composable(route = MAIN_SCREEN){
        LaunchedEffect(key1 = Unit){
            showSnow()
        }
        MainScreen(
            appViewModel = appViewModel,
            notificationsViewModel = notificationsViewModel,
            jobsViewModel = jobsViewModel,
            usersViewModel = usersViewModel,
            profileViewModel = profileViewModel,
            basesViewModel = basesViewModel,
            settingsViewModel = settingsViewModel,
            chatViewModel = chatViewModel
        )
    }
}