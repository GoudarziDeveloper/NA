package com.tinyDeveloper.na.navigation.destinations.main.destinations

import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.tinyDeveloper.na.ui.screens.AppViewModel
import com.tinyDeveloper.na.ui.screens.main.jobs.JobsViewModel
import com.tinyDeveloper.na.ui.screens.main.jobs.job.submissions.SubmissionsViewModel
import com.tinyDeveloper.na.ui.screens.main.jobs.job.submissions.submisson.SubmissionScreen
import com.tinyDeveloper.na.utils.Constants.SUBMISSION_SCREEN
import com.tinyDeveloper.na.utils.Constants.SUBMISSION_SCREEN_ARGUMENT
import com.tinyDeveloper.na.utils.Constants.SUBMISSION_SCREEN_ARGUMENT2
import com.tinyDeveloper.na.utils.Constants.SUBMISSION_SCREEN_TITLE

fun NavGraphBuilder.submissionComposable(
    appViewModel: AppViewModel,
    adminStatus: (Boolean) -> Unit,
    screenTitle: (String) -> Unit,
    jobsViewModel: JobsViewModel,
    submissionsViewModel: SubmissionsViewModel
){
    composable(
        route = "$SUBMISSION_SCREEN/{$SUBMISSION_SCREEN_ARGUMENT}/{$SUBMISSION_SCREEN_ARGUMENT2}",
        arguments = listOf(
            navArgument(SUBMISSION_SCREEN_ARGUMENT) { type = NavType.StringType },
            navArgument(SUBMISSION_SCREEN_ARGUMENT2) { type = NavType.StringType }
        )
    ){
        LaunchedEffect(key1 = Unit){
            screenTitle(SUBMISSION_SCREEN_TITLE)
        }
        it.arguments?.getString(SUBMISSION_SCREEN_ARGUMENT)?.let { id ->
            SubmissionScreen(
                appViewModel = appViewModel,
                id = id,
                jobsViewModel = jobsViewModel,
                submissionsViewModel = submissionsViewModel,
                jobStatus = it.arguments?.getString(SUBMISSION_SCREEN_ARGUMENT2)
            )
        }
    }
}