package com.tinyDeveloper.na.navigation.destinations.main.destinations

import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.tinyDeveloper.na.ui.screens.AppViewModel
import com.tinyDeveloper.na.ui.screens.main.jobs.JobsViewModel
import com.tinyDeveloper.na.ui.screens.main.jobs.job.JobDetailScreen
import com.tinyDeveloper.na.ui.screens.main.jobs.job.submissions.SubmissionsViewModel
import com.tinyDeveloper.na.utils.Constants.JOB_DETAIL_SCREEN
import com.tinyDeveloper.na.utils.Constants.JOB_DETAIL_TITLE
import com.tinyDeveloper.na.utils.Constants.JOB_SCREEN_ARGUMENT
import com.tinyDeveloper.na.utils.Constants.SUBMISSIONS_SCREEN
import com.tinyDeveloper.na.utils.Constants.SUBMISSION_SCREEN

fun NavGraphBuilder.jobDetailsComposable(
    appViewModel: AppViewModel,
    adminStatus: (Boolean) -> Unit,
    screenTitle: (String) -> Unit,
    jobDetail: (Boolean) -> Unit,
    fabNavigate: (String) -> Unit,
    jobsViewModel: JobsViewModel,
    submissionsViewModel: SubmissionsViewModel
){
    composable(
        route = "$JOB_DETAIL_SCREEN/{$JOB_SCREEN_ARGUMENT}",
        arguments = listOf(navArgument(JOB_SCREEN_ARGUMENT){ type = NavType.StringType })
    ) {
        LaunchedEffect(key1 = true){
            adminStatus(appViewModel.roles.value?.getSubmissions == "1")
            screenTitle(JOB_DETAIL_TITLE)
        }
        val id = it.arguments?.getString(JOB_SCREEN_ARGUMENT)
        id?.let {
            JobDetailScreen(
                appViewModel = appViewModel,
                id = id,
                jobsViewModel = jobsViewModel,
                jobDetail = jobDetail,
                submissionsViewModel = submissionsViewModel
            ){ jobId, jobStatus ->
                if (appViewModel.roles.value?.getSubmissions == "1"){
                    fabNavigate("$SUBMISSIONS_SCREEN/$jobId")
                }else{
                    fabNavigate("$SUBMISSION_SCREEN/$jobId/$jobStatus")
                }
            }
        }
    }
}