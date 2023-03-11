package com.tinyDeveloper.na.navigation.destinations.main.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.tinyDeveloper.na.ui.screens.AppViewModel
import com.tinyDeveloper.na.ui.screens.main.chat.PersonalChatScreen
import com.tinyDeveloper.na.ui.screens.main.home.chat.ChatScreen
import com.tinyDeveloper.na.ui.screens.main.home.chat.ChatViewModel
import com.tinyDeveloper.na.utils.Constants

fun NavGraphBuilder.personalChatComposable(
    appViewModel: AppViewModel,
    chatViewModel: ChatViewModel
){
    composable(route = Constants.PERSONAL_CHAT_SCREEN){
        ChatScreen(appViewModel = appViewModel, chatViewModel = chatViewModel)
        //PersonalChatScreen()
    }
}