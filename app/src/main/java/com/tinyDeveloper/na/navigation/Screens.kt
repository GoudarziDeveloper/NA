package com.tinyDeveloper.na.navigation

import androidx.navigation.NavHostController
import com.tinyDeveloper.na.utils.Constants.MAIN_SCREEN
import com.tinyDeveloper.na.utils.Constants.PASSWORD_SCREEN
import com.tinyDeveloper.na.utils.Constants.PHONE_SCREEN
import com.tinyDeveloper.na.utils.Constants.SPLASH_SCREEN
import com.tinyDeveloper.na.utils.Constants.VERIFICATION_SCREEN
import com.tinyDeveloper.na.utils.Constants.WELCOME_SCREEN

class Screens(navController: NavHostController) {
    val splashToLogin: () -> Unit = {
        navController.navigate(route = PHONE_SCREEN) {
            popUpTo(SPLASH_SCREEN){ inclusive = true }
        }
    }
    val splashToMain: () -> Unit = {
        navController.navigate(route = MAIN_SCREEN){
            popUpTo(SPLASH_SCREEN){ inclusive = true }
            popUpTo(VERIFICATION_SCREEN){ inclusive = true }
            popUpTo(PHONE_SCREEN){ inclusive = true }
            popUpTo(WELCOME_SCREEN){ inclusive = true }
            popUpTo(PASSWORD_SCREEN){ inclusive = true }
        }
    }
    val phoneToPassword: () -> Unit = {
        navController.navigate(route = PASSWORD_SCREEN){
            popUpTo(PHONE_SCREEN){
                inclusive = true
            }
        }
    }
    val passwordToPhone: () -> Unit = {
        navController.navigate(route = PHONE_SCREEN){
            popUpTo(PASSWORD_SCREEN){ inclusive = true }
        }
    }
    val passwordToVerification: () -> Unit = {
        navController.navigate(route = VERIFICATION_SCREEN){
            popUpTo(PASSWORD_SCREEN){ inclusive = true }
            popUpTo(PHONE_SCREEN){ inclusive = true }
        }
    }
    val verificationToPhone: () -> Unit = {
        navController.navigate(route = PHONE_SCREEN){
            popUpTo(VERIFICATION_SCREEN){ inclusive = true }
        }
    }
    val verificationToMain: () -> Unit = {
        navController.navigate(route = MAIN_SCREEN){
            popUpTo(route = VERIFICATION_SCREEN){ inclusive = true }
            popUpTo(WELCOME_SCREEN){ inclusive = true }
        }
    }
    val profileToPhoneScreen: () -> Unit = {
        navController.navigate(route = PHONE_SCREEN){
            popUpTo(MAIN_SCREEN){ inclusive = true }
        }
    }
    val splashToWelcome: (isMain: Boolean) -> Unit = {
        navController.navigate(route = "$WELCOME_SCREEN/$it"){
            popUpTo(SPLASH_SCREEN){ inclusive = true }
        }
    }
    val welcomeToPhone: () -> Unit = {
        navController.navigate(route = PHONE_SCREEN){
            popUpTo(WELCOME_SCREEN){ inclusive = true }
        }
    }
}