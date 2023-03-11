package com.tinyDeveloper.na.navigation.destinations.main

import androidx.navigation.NavHostController
import com.tinyDeveloper.na.ui.screens.splash.navigateToNextScreen

class MainScreens(navController: NavHostController) {
    val notifications: () -> Unit = {
        navController.navigate(MainScreensBottomNav.Notifications.route)
    }
    val notification: (id: String) -> Unit = {
        navController.navigate(MainScreensBottomNav.Notification.route + "/" + it)
    }
    val notificationDetails: (id: Int) -> Unit = {
        navController.navigate(MainScreensBottomNav.NotificationDetail.route + "/" + it)
    }
    val jobs: () -> Unit = {
        navController.navigate(MainScreensBottomNav.Jobs.route)
    }
    val job: (id: String) -> Unit = {
        navController.navigate(MainScreensBottomNav.Job.route + "/" + it)
    }
    val jobDetails: (id: String) -> Unit = {
        navController.navigate(MainScreensBottomNav.JobDetails.route + "/" + it)
    }
    val submissions: (id: String) -> Unit = {
        navController.navigate(MainScreensBottomNav.Submissions.route + "/" + it)
    }
    val submission: (id: String, jobStatus: String) -> Unit = { id, jobStatus ->
        navController.navigate(MainScreensBottomNav.Submission.route + "/" + id + "/" + jobStatus)
    }
    val users: () -> Unit = {
        navController.navigate(MainScreensBottomNav.Users.route)
    }
    val user: (phone: String) -> Unit = {
        navController.navigate(MainScreensBottomNav.User.route + "/" + it)
    }
    val profile: () -> Unit = {
        navController.navigate(MainScreensBottomNav.Profile.route)
    }
    val bases: () -> Unit = {
        navController.navigate(MainScreensBottomNav.Bases.route)
    }
    val base: (id: String) -> Unit = {
        navController.navigate(MainScreensBottomNav.Base.route + "/" + it)
    }
    val settings: () -> Unit = {
        navController.navigate(MainScreensBottomNav.Settings.route)
    }
    val personalChat: () -> Unit = {
        navController.navigate(MainScreensBottomNav.PersonalChat.route)
    }
}