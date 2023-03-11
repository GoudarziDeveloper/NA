package com.tinyDeveloper.na.ui.screens.main.home.chat

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tinyDeveloper.na.ui.screens.AppViewModel
import com.tinyDeveloper.na.utils.Constants.APP_CHAT_KEY
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.compose.ui.theme.StreamShapes
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.tinyDeveloper.na.R
import com.tinyDeveloper.na.ui.component.CircleProgress
import com.tinyDeveloper.na.ui.component.EmptyScreen
import com.tinyDeveloper.na.ui.theme.LARGE_PADDING
import com.tinyDeveloper.na.ui.theme.MEDIUM_PADDING
import com.tinyDeveloper.na.utils.Constants.BASE_URL
import com.tinyDeveloper.na.utils.Constants.SERVER_EXT
import com.tinyDeveloper.na.utils.Constants.UPLOAD_URL
import io.getstream.chat.android.compose.ui.messages.MessagesScreen
import io.getstream.chat.android.compose.ui.util.mirrorRtl
import kotlinx.coroutines.delay
import kotlin.random.Random


@Composable
fun ChatScreen(
    appViewModel: AppViewModel,
    chatViewModel: ChatViewModel
){
    var isLoading by remember{ mutableStateOf(false) }
    var error by remember{ mutableStateOf(false) }
    var success by remember{ mutableStateOf(false) }
    val loginEvent = chatViewModel.loginEvent.value
    var progressNumber by rememberSaveable{ mutableStateOf(0f) }

    LaunchedEffect(key1 = Unit){
        if (progressNumber == 0f) progressNumber = 5f
    }

    LaunchedEffect(key1 = progressNumber){
        delay(Random.nextLong(0,500))
        if (progressNumber < 94) progressNumber += 5
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        LaunchedEffect(key1 = loginEvent){
            when(loginEvent){
                is ChatViewModel.LogInEvent.Success -> {
                    isLoading = false
                    success = true
                }
                is ChatViewModel.LogInEvent.Loading -> {
                    isLoading = true
                }
                is ChatViewModel.LogInEvent.Error -> {
                    isLoading = false
                    error = true
                }
                is ChatViewModel.LogInEvent.None -> {
                    if(appViewModel.chatInfo.value != null){
                        Log.i("image", "$BASE_URL$SERVER_EXT$UPLOAD_URL" + appViewModel.chatInfo.value!!.image)
                        chatViewModel.chatLogin(
                            username = appViewModel.chatInfo.value!!.userName,
                            token = appViewModel.chatInfo.value!!.token,
                            name = appViewModel.chatInfo.value!!.name,
                            image = "$BASE_URL$SERVER_EXT$UPLOAD_URL" + appViewModel.chatInfo.value!!.image
                        )
                    }
                }

                else -> {}
            }
        }
        if(success && appViewModel.chatInfo.value?.chanel != null){
            /*CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr ) {

            }*/
            ChatTheme (
                shapes = StreamShapes.defaultShapes().copy(
                    avatar = RoundedCornerShape(8.dp),
                    attachment = RoundedCornerShape(16.dp),
                    myMessageBubble = RoundedCornerShape(16.dp),
                    otherMessageBubble = RoundedCornerShape(16.dp),
                    inputField = RoundedCornerShape(LARGE_PADDING)
                )
            ) {
                MessagesScreen(
                    channelId = appViewModel.chatInfo.value!!.chanel,
                    messageLimit = 20,
                    showHeader = false
                )
            }

        } else if(error){
            EmptyScreen(
                image = R.drawable.ic_empty_screen2,
                text = "سرور درخواست نشست گفتگو را رد کرد!\nسرور با کمبود منابع مواجه گردید.",
                isButton = true,
                buttonTitle = "تلاش مجدد"
            ){
                error = false
                success = false
                isLoading = true
                progressNumber = 5f
                chatViewModel.chatLogin()
            }
        } else if(isLoading){
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircleProgress(
                    number = progressNumber
                )
                Spacer(modifier = Modifier.height(MEDIUM_PADDING))
                Text(
                    text = "درحال ایجاد گفتگو...",
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.titleSmall.fontSize
                )
            }
        }
    }
}