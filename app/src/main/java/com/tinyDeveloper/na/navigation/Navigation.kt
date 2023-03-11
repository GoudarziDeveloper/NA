package com.tinyDeveloper.na.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.tinyDeveloper.na.navigation.destinations.*
import com.tinyDeveloper.na.ui.screens.AppViewModel
import com.tinyDeveloper.na.ui.screens.main.bases.BasesViewModel
import com.tinyDeveloper.na.ui.screens.main.home.chat.ChatViewModel
import com.tinyDeveloper.na.ui.screens.main.jobs.JobsViewModel
import com.tinyDeveloper.na.ui.screens.main.home.NotificationsViewModel
import com.tinyDeveloper.na.ui.screens.main.profile.ProfileViewModel
import com.tinyDeveloper.na.ui.screens.main.settings.SettingsViewModel
import com.tinyDeveloper.na.ui.screens.main.useers.UsersViewModel
import com.tinyDeveloper.na.utils.Constants.SPLASH_SCREEN

@Composable
fun SetupNavigation(
    navController: NavHostController,
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
    val screens = remember(navController){
        Screens(navController = navController)
    }
    NavHost(navController = navController, startDestination = SPLASH_SCREEN){
        splashComposable(
            navigateToMainScreen = screens.splashToMain,
            navigateToPhoneScreen = screens.splashToLogin,
            appViewModel = appViewModel,
            navigateToWelcomeScreen = screens.splashToWelcome
        )
        welcomeComposable(
            navigateToPhoneScreen = {
                screens.welcomeToPhone()
            },
            navigateToMainScreen = screens.splashToMain
        )
        phoneComposable(navigateToPasswordScreen = screens.phoneToPassword, appViewModel = appViewModel)
        passwordComposable(
            navigateToPhoneScreen = screens.passwordToPhone,
            navigateToVerificationScreen = screens.passwordToVerification,
            appViewModel = appViewModel
        )
        verificationComposable(
            navigateToMainScreen = screens.verificationToMain,
            navigateToPhoneScreen = screens.verificationToPhone,
            appViewModel = appViewModel
        )
        mainComposable(
            appViewModel = appViewModel,
            notificationsViewModel = notificationsViewModel,
            jobsViewModel = jobsViewModel,
            usersViewModel = usersViewModel,
            profileViewModel = profileViewModel,
            basesViewModel = basesViewModel,
            settingsViewModel = settingsViewModel,
            chatViewModel = chatViewModel,
            showSnow = showSnow
        )
    }
}