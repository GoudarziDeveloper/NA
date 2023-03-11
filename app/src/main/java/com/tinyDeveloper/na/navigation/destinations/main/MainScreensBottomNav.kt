package com.tinyDeveloper.na.navigation.destinations.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.tinyDeveloper.na.utils.Constants.BASES_SCREEN
import com.tinyDeveloper.na.utils.Constants.BASES_SCREEN_TITLE
import com.tinyDeveloper.na.utils.Constants.BASE_SCREEN
import com.tinyDeveloper.na.utils.Constants.BASE_SCREEN_TITLE
import com.tinyDeveloper.na.utils.Constants.JOBS_SCREEN
import com.tinyDeveloper.na.utils.Constants.JOB_DETAIL_SCREEN
import com.tinyDeveloper.na.utils.Constants.JOBS_SCREEN_TITLE
import com.tinyDeveloper.na.utils.Constants.JOB_SCREEN
import com.tinyDeveloper.na.utils.Constants.NOTIFICATIONS_SCREEN
import com.tinyDeveloper.na.utils.Constants.NOTIFICATIONS_SCREEN_TITLE
import com.tinyDeveloper.na.utils.Constants.NOTIFICATION_DETAILS_SCREEN
import com.tinyDeveloper.na.utils.Constants.NOTIFICATION_SCREEN
import com.tinyDeveloper.na.utils.Constants.PERSONAL_CHAT_SCREEN
import com.tinyDeveloper.na.utils.Constants.PERSONAL_CHAT_SCREEN_TITLE
import com.tinyDeveloper.na.utils.Constants.PROFILE_SCREEN
import com.tinyDeveloper.na.utils.Constants.PROFILE_SCREEN_TITLE
import com.tinyDeveloper.na.utils.Constants.SETTINGS_SCREEN
import com.tinyDeveloper.na.utils.Constants.SETTINGS_SCREEN_TITLE
import com.tinyDeveloper.na.utils.Constants.SUBMISSIONS_SCREEN
import com.tinyDeveloper.na.utils.Constants.SUBMISSION_SCREEN
import com.tinyDeveloper.na.utils.Constants.USERS_SCREEN
import com.tinyDeveloper.na.utils.Constants.USERS_SCREEN_TITLE
import com.tinyDeveloper.na.utils.Constants.USER_SCREEN
import com.tinyDeveloper.na.utils.Constants.USER_TITLE

sealed class MainScreensBottomNav(
    val route: String,
    val title: String,
    val icon: ImageVector
){
    object Notifications: MainScreensBottomNav(
        route = NOTIFICATIONS_SCREEN,
        title = NOTIFICATIONS_SCREEN_TITLE,
        icon = Icons.Default.Notifications
    )

    object Notification: MainScreensBottomNav(
        route = NOTIFICATION_SCREEN,
        title = NOTIFICATIONS_SCREEN_TITLE,
        icon = Icons.Default.Notifications
    )

    object NotificationDetail: MainScreensBottomNav(
        route = NOTIFICATION_DETAILS_SCREEN,
        title = NOTIFICATIONS_SCREEN_TITLE,
        icon = Icons.Default.Notifications
    )

    object Jobs: MainScreensBottomNav(
        route = JOBS_SCREEN,
        title = JOBS_SCREEN_TITLE,
        icon = Icons.Default.List
    )

    object Job: MainScreensBottomNav(
        route = JOB_SCREEN,
        title = JOBS_SCREEN_TITLE,
        icon = Icons.Default.List
    )

    object JobDetails: MainScreensBottomNav(
        route = JOB_DETAIL_SCREEN,
        title = JOBS_SCREEN_TITLE,
        icon = Icons.Default.List
    )

    object Submissions: MainScreensBottomNav(
        route = SUBMISSIONS_SCREEN,
        title = JOBS_SCREEN_TITLE,
        icon = Icons.Default.List
    )

    object Submission: MainScreensBottomNav(
        route = SUBMISSION_SCREEN,
        title = JOBS_SCREEN_TITLE,
        icon = Icons.Default.List
    )

    object Users: MainScreensBottomNav(
        route = USERS_SCREEN,
        title = USERS_SCREEN_TITLE,
        icon = Icons.Default.AccountCircle
    )

    object User: MainScreensBottomNav(
        route = USER_SCREEN,
        title = USER_TITLE,
        icon = Icons.Default.AccountBox
    )

    object Profile: MainScreensBottomNav(
        route = PROFILE_SCREEN,
        title = PROFILE_SCREEN_TITLE,
        icon = Icons.Default.Face
    )
    
    object Bases: MainScreensBottomNav(
        route = BASES_SCREEN,
        title = BASES_SCREEN_TITLE,
        icon = Icons.Default.Home
    )

    object Base: MainScreensBottomNav(
        route = BASE_SCREEN,
        title = BASE_SCREEN_TITLE,
        icon = Icons.Default.Home
    )

    object Settings: MainScreensBottomNav(
        route = SETTINGS_SCREEN,
        title = SETTINGS_SCREEN_TITLE,
        icon = Icons.Default.Settings
    )

    object PersonalChat: MainScreensBottomNav(
        route = PERSONAL_CHAT_SCREEN,
        title = PERSONAL_CHAT_SCREEN_TITLE,
        icon = Icons.Default.Settings
    )
}