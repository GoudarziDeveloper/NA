package com.tinyDeveloper.na.ui.screens.main.home

import android.graphics.Bitmap
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tinyDeveloper.na.domain.models.request.BasicRequest
import com.tinyDeveloper.na.domain.models.request.notifications.AddNotificationRequest
import com.tinyDeveloper.na.domain.models.request.notifications.DeleteNotificationRequest
import com.tinyDeveloper.na.domain.models.request.notifications.GetNotificationRequest
import com.tinyDeveloper.na.domain.models.request.notifications.UpdateNotificationRequest
import com.tinyDeveloper.na.domain.models.response.BasicResponse
import com.tinyDeveloper.na.domain.models.response.notifications.GetNotificationResponse
import com.tinyDeveloper.na.domain.models.response.notifications.Notification
import com.tinyDeveloper.na.domain.models.response.notifications.NotificationsResponse
import com.tinyDeveloper.na.domain.use_cases.UseCases
import com.tinyDeveloper.na.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(private val useCases: UseCases): ViewModel() {
    private val _commonNotificationsResponse: MutableState<Resource<NotificationsResponse>?> = mutableStateOf(null)
    val commonNotificationsResponse: State<Resource<NotificationsResponse>?> = _commonNotificationsResponse

    private val _privateNotificationsResponse: MutableState<Resource<NotificationsResponse>?> = mutableStateOf(null)
    val privateNotificationsResponse: State<Resource<NotificationsResponse>?> = _privateNotificationsResponse

    private val _getNotificationResponse: MutableState<Resource<GetNotificationResponse>?> = mutableStateOf(null)
    val getNotificationResponse: State<Resource<GetNotificationResponse>?> = _getNotificationResponse

    private val _notificationRequest: MutableState<AddNotificationRequest> = mutableStateOf(AddNotificationRequest())
    val notificationRequest: State<AddNotificationRequest> = _notificationRequest

    private val _notificationResponse: MutableState<Resource<BasicResponse>?> = mutableStateOf(null)
    val notificationResponse: State<Resource<BasicResponse>?> = _notificationResponse

    private val _notificationId: MutableState<String> = mutableStateOf("-1")
    val notificationId: State<String> = _notificationId

    private val _image: MutableState<Bitmap?> =  mutableStateOf(null)
    val image: State<Bitmap?> = _image

    private val _currentPage: MutableState<Int> = mutableStateOf(1)
    val currentPage: State<Int> = _currentPage

    fun setCurrentPage(currentPage:Int){
        _currentPage.value = currentPage
    }

    fun emptyImage(){
        _image.value = null
    }

    fun emptyNotificationResponse(){
        _notificationResponse.value = null
    }

    fun updateImage(image: Bitmap?){
        _image.value = image
    }

    fun setNotificationId(id: String){
        _notificationId.value = id
    }

    fun getCommonNotifications(phone: String, password: String){
        _commonNotificationsResponse.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO){
            _commonNotificationsResponse.value = useCases.getCommonNotifications(
                basicRequest = BasicRequest(phone = phone, password = password)
            )
        }
    }

    fun getAllCommonNotifications(phone: String, password: String){
        _commonNotificationsResponse.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            _commonNotificationsResponse.value = useCases.getAllCommonNotifications(
                basicRequest = BasicRequest(phone = phone, password = password)
            )
        }
    }


    fun getPrivateNotifications(phone: String, password: String){
        _privateNotificationsResponse.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO){
            _privateNotificationsResponse.value = useCases.getPrivateNotifications(
                basicRequest = BasicRequest(phone = phone, password = password)
            )
        }
    }


    fun getAllPrivateNotifications(phone: String, password: String){
        _privateNotificationsResponse.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO){
            _privateNotificationsResponse.value = useCases.getAllPrivateNotifications(
                basicRequest = BasicRequest(phone = phone, password = password)
            )
        }
    }

    fun getNotification(phone: String, password: String, id: String){
        _getNotificationResponse.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO){
            _getNotificationResponse.value = useCases.getNotification(
                getNotificationRequest = GetNotificationRequest(phone = phone, password = password, id = id)
            )
        }
    }

    fun addNotification(phone: String, password: String){
        _notificationResponse.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO){
            _notificationResponse.value = useCases.addNotification(
                addNotificationRequest = notificationRequest.value.copy(phone = phone, password = password)
            )
        }
    }

    fun deleteNotification(id: String, phone: String, password: String){
        _notificationResponse.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            _notificationResponse.value = useCases.deleteNotification(
                DeleteNotificationRequest(
                    phone = phone,
                    password = password,
                    id = id
                )
            )
        }
    }

    fun findNotification(id: String): Notification?{
        var notification: Notification? = null
        commonNotificationsResponse.value?.data?.notifications?.forEach{
            if (it.id == id){
                notification = it
            }
        }
        privateNotificationsResponse.value?.data?.notifications?.forEach {
            if (it.id == id){
                notification = it
            }
        }
        return notification
    }

    fun hideNotification(id: String){
        _commonNotificationsResponse.value?.data?.notifications?.forEach{
            if (it.id == id){
                it.status = "deleted"
            }
        }
        _privateNotificationsResponse.value?.data?.notifications?.forEach {
            if (it.id == id){
                it.status = "deleted"
            }
        }
    }

    fun updateNotification(id: String, imageNotUpdate: Boolean, phone: String, password: String){
        _notificationResponse.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
        val updateRequest = UpdateNotificationRequest(
            baseId = notificationRequest.value.baseId,
            description = notificationRequest.value.description,
            image = if (imageNotUpdate) "-1" else notificationRequest.value.image,
            maxShowTime = notificationRequest.value.maxShowTime,
            password = password,
            phone = phone,
            status = notificationRequest.value.status,
            title = notificationRequest.value.title,
            id = id
        )
            _notificationResponse.value = useCases.updateNotification(
                updateNotificationRequest = updateRequest
            )
        }
    }

    fun notificationFieldUpdate(
        phone: String = notificationRequest.value.phone,
        password: String = notificationRequest.value.password,
        baseId: String = notificationRequest.value.baseId,
        title: String = notificationRequest.value.title,
        description: String = notificationRequest.value.description,
        maxShowTime: String = notificationRequest.value.maxShowTime,
        image: String = notificationRequest.value.image,
        status: String = notificationRequest.value.status,
        empty: Boolean = false
    ){
        if (!empty){
            _notificationRequest.value = AddNotificationRequest(
                phone = phone,
                password = password,
                baseId = baseId,
                title = title,
                description = description,
                maxShowTime = maxShowTime,
                image = image,
                status = status
            )
        }else {
            _notificationRequest.value = AddNotificationRequest()
        }
    }
}