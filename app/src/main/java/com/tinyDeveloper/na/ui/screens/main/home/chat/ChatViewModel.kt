package com.tinyDeveloper.na.ui.screens.main.home.chat

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.models.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(private val client: ChatClient): ViewModel(){

    private val _loginEvent: MutableState<LogInEvent?> = mutableStateOf(LogInEvent.None)
    val loginEvent: State<LogInEvent?> = _loginEvent

    private var _chatRequest: MutableState<ChatRequest?> = mutableStateOf(null)
    private val chatRequest: State<ChatRequest?> = _chatRequest

    fun chatLogin(
        username: String = chatRequest.value?.username?:"",
        token: String = chatRequest.value?.token?:"" ,
        name: String = chatRequest.value?.name?:"",
        image: String = chatRequest.value?.image?:"",
        again: Boolean = false
    ) {
        _chatRequest.value = null
        _chatRequest.value = ChatRequest(username = username, token = token, name = name, image = image)
        val user = User(id = username, name = name, image = image)
        _loginEvent.value = null
        _loginEvent.value = LogInEvent.Loading
        loadingTimeout()
        //connectCheck()
        try {
            client.connectUser(
                user = user,
                token = token
            ).enqueue { result ->
                if (result.isSuccess) {
                    _loginEvent.value = null
                    _loginEvent.value = LogInEvent.Success
                } else {
                    _loginEvent.value = null
                    _loginEvent.value = LogInEvent.Error
                }
            }
        }catch (e: Exception){
            _loginEvent.value = null
            _loginEvent.value = LogInEvent.Error
        }
        if (again){
            chatLogin()
        }
    }

    private fun loadingTimeout(){
        viewModelScope.launch {
            delay(15000)
            if (loginEvent.value is LogInEvent.Loading){
                _loginEvent.value = null
                _loginEvent.value = LogInEvent.Error
            }
        }
    }

    sealed class LogInEvent {
        object Loading : LogInEvent()
        object Error: LogInEvent()
        object Success : LogInEvent()
        object None: LogInEvent()
    }

    private fun connectCheck(){
        viewModelScope.launch {
            while (true){
                delay(2000)
                if(chatRequest.value != null && !connected() || loginEvent.value is LogInEvent.Error){
                    _loginEvent.value = null
                    _loginEvent.value = LogInEvent.Loading
                    chatLogin(
                        username = chatRequest.value!!.username,
                        token = chatRequest.value!!.token,
                        name = chatRequest.value!!.name,
                        image = chatRequest.value!!.image
                    )
                }
            }
        }
    }

    private fun connected(): Boolean{
        return client.isSocketConnected()
    }
}