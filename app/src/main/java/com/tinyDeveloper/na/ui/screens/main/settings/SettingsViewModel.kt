package com.tinyDeveloper.na.ui.screens.main.settings

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tinyDeveloper.na.domain.models.request.base_info.update_base_info.UpdateBaseInfoRequest
import com.tinyDeveloper.na.domain.models.response.BasicResponse
import com.tinyDeveloper.na.domain.models.response.base_info.BaseInfoResponse
import com.tinyDeveloper.na.domain.use_cases.UseCases
import com.tinyDeveloper.na.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val useCases: UseCases): ViewModel() {
    private val _getBaseInfoResponse: MutableState<Resource<BaseInfoResponse>?> = mutableStateOf(null)
    val getBaseInfoResponse: State<Resource<BaseInfoResponse>?> = _getBaseInfoResponse

    private val _basicResponse: MutableState<Resource<BasicResponse>?> = mutableStateOf(null)
    val basicResponse: State<Resource<BasicResponse>?> = _basicResponse

    private val _updateBaseInfoRequest: MutableState<UpdateBaseInfoRequest?> = mutableStateOf(null)
    val updateBaseInfoRequest: State<UpdateBaseInfoRequest?> = _updateBaseInfoRequest

    fun getBaseInfo(){
        _getBaseInfoResponse.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO){
            _getBaseInfoResponse.value = useCases.getBaseInfoUseCase()
        }
    }

    fun updateBaseInfo(){
        _basicResponse.value = Resource.Loading()
        updateBaseInfoRequest.value?.let {
            viewModelScope.launch(Dispatchers.IO){
                _basicResponse.value = useCases.updateBaseInfo(updateBaseInfo = it)
            }
        }
    }

    fun updateFields(
        appEnabled: String = updateBaseInfoRequest.value?.appEnabled?: "",
        isSnow: String = updateBaseInfoRequest.value?.isSnow?: "",
        checkCodeUrl: String = updateBaseInfoRequest.value?.checkCodeUrl?: "",
        manager: String = updateBaseInfoRequest.value?.manager?: "",
        id: String = updateBaseInfoRequest.value?.id?: "",
        part: String = updateBaseInfoRequest.value?.part?: "",
        lastVersion: String = updateBaseInfoRequest.value?.lastVersion?: "",
        messageEnabled: String = updateBaseInfoRequest.value?.messageEnabled?: "",
        messageText: String = updateBaseInfoRequest.value?.messageText?: "",
        messageTitle: String = updateBaseInfoRequest.value?.messageTitle?: "",
        minVersion: String = updateBaseInfoRequest.value?.minVersion?: "",
        center: String = updateBaseInfoRequest.value?.center ?: "",
        password: String = updateBaseInfoRequest.value?.password?: "",
        phone: String = updateBaseInfoRequest.value?.phone?: "",
        sendCodeUrl: String = updateBaseInfoRequest.value?.sendCodeUrl?: "",
        status: String = updateBaseInfoRequest.value?.status?: "",
        stopText: String = updateBaseInfoRequest.value?.stopText?: "",
        stopTitle: String = updateBaseInfoRequest.value?.stopTitle?: "",
        updateLink: String = updateBaseInfoRequest.value?.updateLink?: "",
        updateText: String = updateBaseInfoRequest.value?.updateText?: "",
        updateTitle: String = updateBaseInfoRequest.value?.updateTitle?: "",
        uploadUrl: String = updateBaseInfoRequest.value?.uploadUrl?: "",
    ){
        _updateBaseInfoRequest.value = UpdateBaseInfoRequest(
            appEnabled = appEnabled,
            isSnow = isSnow,
            checkCodeUrl = checkCodeUrl,
            id = id,
            lastVersion = lastVersion,
            messageEnabled = messageEnabled,
            messageText = messageText,
            messageTitle = messageTitle,
            minVersion = minVersion,
            password = password,
            phone = phone,
            sendCodeUrl = sendCodeUrl,
            status = status,
            stopText = stopText,
            stopTitle = stopTitle,
            updateLink = updateLink,
            updateText = updateText,
            updateTitle = updateTitle,
            uploadUrl = uploadUrl,
            manager = manager,
            part = part,
            center = center
        )
    }
}