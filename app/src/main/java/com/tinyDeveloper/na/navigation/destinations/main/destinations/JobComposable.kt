package com.tinyDeveloper.na.navigation.destinations.main.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.tinyDeveloper.na.ui.screens.AppViewModel
import com.tinyDeveloper.na.ui.screens.main.jobs.JobsViewModel
import com.tinyDeveloper.na.ui.screens.main.jobs.job.JobScreen
import com.tinyDeveloper.na.utils.Constants
import com.tinyDeveloper.na.utils.Constants.JOB_SCREEN
import com.tinyDeveloper.na.utils.Constants.JOB_SCREEN_ARGUMENT
import com.tinyDeveloper.na.utils.Constants.JOB_TITLE

fun NavGraphBuilder.jobComposable(
    appViewModel: AppViewModel,
    adminStatus: (Boolean) -> Unit,
    screenTitle: (String) -> Unit,
    navigateToJobsScreen: () -> Unit,
    jobsViewModel: JobsViewModel
){
    composable(
        route = "$JOB_SCREEN/{$JOB_SCREEN_ARGUMENT}",
        arguments = listOf(navArgument(JOB_SCREEN_ARGUMENT){ type = NavType.StringType })
    ){
        screenTitle(JOB_TITLE)
        val id = it.arguments?.getString(JOB_SCREEN_ARGUMENT)
        id?.let{
            JobScreen(
                appViewModel = appViewModel,
                adminStatus = adminStatus,
                id = id,
                navigateToJobsScreen = navigateToJobsScreen,
                jobsViewModel = jobsViewModel
            )
        }
    }
}