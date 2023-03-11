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
import com.tinyDeveloper.na.ui.screens.main.jobs.JobsScreen
import com.tinyDeveloper.na.ui.screens.main.jobs.JobsViewModel
import com.tinyDeveloper.na.utils.Constants
import com.tinyDeveloper.na.utils.Constants.JOBS_SCREEN
import com.tinyDeveloper.na.utils.Constants.JOBS_SCREEN_TITLE
import com.tinyDeveloper.na.utils.Constants.JOB_SCREEN
import com.tinyDeveloper.na.utils.Constants.JOB_SCREEN_ARGUMENT

fun NavGraphBuilder.jobsComposable(
    appViewModel: AppViewModel,
    adminStatus: (Boolean) -> Unit,
    screenTitle: (String) -> Unit,
    fabNavigate: (route: String) -> Unit,
    navigateToJobScreen: (String) -> Unit,
    navigateToJobDetailsScreen: (String) -> Unit,
    jobsViewModel: JobsViewModel
){
    composable(route = JOBS_SCREEN){
        var isAppeared by remember { mutableStateOf(false) }
        LaunchedEffect(key1 = Unit){
            isAppeared = true
            fabNavigate("$JOB_SCREEN/-1")
            screenTitle(JOBS_SCREEN_TITLE)
        }
        AnimatedVisibility(
            visible = isAppeared,
            enter = fadeIn(
                animationSpec = tween(
                    durationMillis = Constants.SCREENS_DURATION
                )
            )
        ) {
            JobsScreen(
                appViewModel = appViewModel,
                adminStatus = adminStatus,
                navigateToJobScreen = navigateToJobScreen,
                navigateToJobDetailsScreen = navigateToJobDetailsScreen,
                jobsViewModel = jobsViewModel
            )
        }
    }
}