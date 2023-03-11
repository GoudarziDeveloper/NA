package com.tinyDeveloper.na.navigation.destinations.main.destinations

import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.tinyDeveloper.na.ui.screens.AppViewModel
import com.tinyDeveloper.na.ui.screens.main.jobs.job.submissions.SubmissionsScreen
import com.tinyDeveloper.na.ui.screens.main.jobs.job.submissions.SubmissionsViewModel
import com.tinyDeveloper.na.utils.Constants.SUBMISSIONS_SCREEN
import com.tinyDeveloper.na.utils.Constants.SUBMISSIONS_SCREEN_ARGUMENT
import com.tinyDeveloper.na.utils.Constants.SUBMISSIONS_SCREEN_TITLE

fun NavGraphBuilder.submissionsComposable(
    appViewModel: AppViewModel,
    adminStatus: (Boolean) -> Unit,
    screenTitle: (String) -> Unit,
    navigateToSubmissionScreen: (jobId: String, jobsStatus: String) -> Unit,
    submissionsViewModel: SubmissionsViewModel
){
    composable(
        route = "$SUBMISSIONS_SCREEN/{$SUBMISSIONS_SCREEN_ARGUMENT}",
        arguments = listOf(navArgument(SUBMISSIONS_SCREEN_ARGUMENT){ type = NavType.StringType })
    ){
        LaunchedEffect(key1 = true){
            screenTitle(SUBMISSIONS_SCREEN_TITLE)
        }
        val id = it.arguments?.getString(SUBMISSIONS_SCREEN_ARGUMENT)
        id?.let {
            SubmissionsScreen(
                appViewModel = appViewModel,
                id = id,
                navigateToSubmissionScreen = navigateToSubmissionScreen,
                submissionsViewModel = submissionsViewModel
            )
        }
    }
}