package com.tinyDeveloper.na.navigation.destinations.main.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.tinyDeveloper.na.ui.screens.AppViewModel
import com.tinyDeveloper.na.ui.screens.main.bases.BasesViewModel
import com.tinyDeveloper.na.ui.screens.main.bases.base.BaseScreen
import com.tinyDeveloper.na.utils.Constants
import com.tinyDeveloper.na.utils.Constants.BASE_SCREEN_ARGUMENT

fun NavGraphBuilder.baseComposable(
    appViewModel: AppViewModel,
    navigateToBasesScreen: () -> Unit,
    screenTitle: (String) -> Unit,
    basesViewModel: BasesViewModel){
    composable(route = Constants.BASE_SCREEN+"/{$BASE_SCREEN_ARGUMENT}", arguments = listOf(navArgument(BASE_SCREEN_ARGUMENT){ type = NavType.StringType })){
        screenTitle(Constants.BASE_SCREEN_TITLE)
        it.arguments?.getString(BASE_SCREEN_ARGUMENT)?.let{ id ->
            BaseScreen(appViewModel = appViewModel, id = id, navigateToBasesScreen = navigateToBasesScreen, basesViewModel = basesViewModel)
        }
    }
}