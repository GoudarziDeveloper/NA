package com.tinyDeveloper.na.navigation.destinations.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.tinyDeveloper.na.navigation.destinations.main.destinations.*
import com.tinyDeveloper.na.ui.screens.AppViewModel
import com.tinyDeveloper.na.ui.screens.main.bases.BasesViewModel
import com.tinyDeveloper.na.ui.screens.main.home.chat.ChatViewModel
import com.tinyDeveloper.na.ui.screens.main.jobs.JobsViewModel
import com.tinyDeveloper.na.ui.screens.main.jobs.job.submissions.SubmissionsViewModel
import com.tinyDeveloper.na.ui.screens.main.home.NotificationsViewModel
import com.tinyDeveloper.na.ui.screens.main.profile.ProfileViewModel
import com.tinyDeveloper.na.ui.screens.main.settings.SettingsViewModel
import com.tinyDeveloper.na.ui.screens.main.useers.UsersViewModel
import com.tinyDeveloper.na.ui.screens.splash.navigateToNextScreen
import io.getstream.chat.android.compose.viewmodel.messages.AttachmentsPickerViewModel
import io.getstream.chat.android.compose.viewmodel.messages.MessageComposerViewModel
import io.getstream.chat.android.compose.viewmodel.messages.MessageListViewModel

@Composable
fun SetupMainScreenNavigation(
    navController: NavHostController,
    appViewModel: AppViewModel,
    adminStatus: (Boolean) -> Unit,
    screenTitle: (String) -> Unit,
    fabNavigate: (route: String) -> Unit,
    jobDetail: (Boolean) -> Unit,
    notificationsViewModel: NotificationsViewModel,
    jobsViewModel: JobsViewModel,
    usersViewModel: UsersViewModel,
    profileViewModel: ProfileViewModel,
    firstScreen: String,
    basesViewModel: BasesViewModel,
    settingsViewModel: SettingsViewModel,
    chatViewModel: ChatViewModel,
    submissionsViewModel: SubmissionsViewModel = hiltViewModel(),
){
    val screens = remember(navController){
        MainScreens(navController = navController)
    }

    NavHost(navController = navController, startDestination = firstScreen) {
        notificationsComposable(
            appViewModel = appViewModel,
            navigateToNotificationScreen = screens.notificationDetails,
            adminStatus = adminStatus, screenTitle = screenTitle,
            fabNavigate = fabNavigate,
            navigateToUpdate = screens.notification,
            notificationsViewModel = notificationsViewModel,
            chatViewModel = chatViewModel,
        )
        notificationDetailsComposable(
            appViewModel = appViewModel,
            screenTitle = screenTitle,
            adminStatus = adminStatus,
            notificationsViewModel = notificationsViewModel
        )
        jobsComposable(
            appViewModel = appViewModel,
            screenTitle = screenTitle,
            adminStatus = adminStatus,
            fabNavigate = fabNavigate,
            navigateToJobScreen = screens.job,
            navigateToJobDetailsScreen = screens.jobDetails,
            jobsViewModel = jobsViewModel
        )
        jobDetailsComposable(
            appViewModel = appViewModel,
            screenTitle = screenTitle,
            adminStatus = adminStatus,
            jobDetail = jobDetail,
            fabNavigate = fabNavigate,
            jobsViewModel = jobsViewModel,
            submissionsViewModel = submissionsViewModel
        )
        submissionsComposable(
            appViewModel = appViewModel,
            screenTitle = screenTitle,
            adminStatus = adminStatus,
            navigateToSubmissionScreen = screens.submission,
            submissionsViewModel = submissionsViewModel
        )
        submissionComposable(
            appViewModel = appViewModel,
            screenTitle = screenTitle,
            adminStatus = adminStatus,
            jobsViewModel = jobsViewModel,
            submissionsViewModel = submissionsViewModel
        )
        usersComposable(
            appViewModel = appViewModel,
            screenTitle = screenTitle,
            adminStatus = adminStatus,
            fabNavigate = fabNavigate,
            navigateToUserScreen = screens.user,
            usersViewModel = usersViewModel,
            navigateToPersonalChat = screens.personalChat
        )
        profileComposable(
            appViewModel = appViewModel,
            screenTitle = screenTitle,
            adminStatus = adminStatus,
            profileViewModel = profileViewModel,
            usersViewModel = usersViewModel
        )
        notificationComposable(
            appViewModel = appViewModel,
            screenTitle = screenTitle,
            adminStatus = adminStatus,
            navigateToNotificationsScreen = screens.notifications,
            notificationsViewModel = notificationsViewModel
        )
        jobComposable(
            appViewModel = appViewModel,
            adminStatus = adminStatus,
            screenTitle = screenTitle,
            navigateToJobsScreen = screens.jobs,
            jobsViewModel = jobsViewModel
        )
        userComposable(
            appViewModel = appViewModel,
            adminStatus = adminStatus,
            screenTitle = screenTitle,
            navigateToUsersScreen = screens.users,
            usersViewModel = usersViewModel
        )
        basesComposable(
            appViewModel = appViewModel,
            adminStatus = adminStatus,
            fabNavigate = fabNavigate,
            screenTitle = screenTitle,
            navigateToBaseScreen = screens.base,
            basesViewModel = basesViewModel
        )
        baseComposable(
            appViewModel = appViewModel,
            navigateToBasesScreen = screens.bases,
            screenTitle = screenTitle,
            basesViewModel = basesViewModel
        )
        settingsComposable(
            appViewModel = appViewModel,
            screenTitle = screenTitle,
            settingsViewModel = settingsViewModel
        )
        personalChatComposable(
            appViewModel = appViewModel,
            chatViewModel = chatViewModel
        )
    }
}