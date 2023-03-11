package com.tinyDeveloper.na.data.repositories.remote_data_surce

import android.util.Log
import com.tinyDeveloper.na.data.remote.NaApi
import com.tinyDeveloper.na.domain.models.request.BasicRequest
import com.tinyDeveloper.na.domain.models.request.notifications.AddNotificationRequest
import com.tinyDeveloper.na.domain.models.request.notifications.DeleteNotificationRequest
import com.tinyDeveloper.na.domain.models.request.notifications.GetNotificationRequest
import com.tinyDeveloper.na.domain.models.request.notifications.UpdateNotificationRequest
import com.tinyDeveloper.na.domain.models.response.BasicResponse
import com.tinyDeveloper.na.domain.models.response.notifications.GetNotificationResponse
import com.tinyDeveloper.na.domain.models.response.notifications.NotificationsResponse
import com.tinyDeveloper.na.domain.repositories.NotificationsSource
import com.tinyDeveloper.na.utils.Resource

class NotificationsSourceImpl(private val naApi: NaApi): NotificationsSource {
    override suspend fun getNotifications(url: String, basicRequest: BasicRequest): Resource<NotificationsResponse> {
        return try{
            Resource.Success(naApi.getNotifications(url = url, basicRequest = basicRequest))
        } catch(e: java.lang.Exception){
            Log.i("notifications", e.message.toString())
            Resource.Error(e.message)
        }
    }

    override suspend fun addNotification(addNotificationRequest: AddNotificationRequest): Resource<BasicResponse> {
        return try {
            Resource.Success(naApi.addNotification(addNotificationRequest = addNotificationRequest))
        } catch (e: Exception){
            Resource.Error(e.message)
        }
    }

    override suspend fun getNotification(getNotificationRequest: GetNotificationRequest): Resource<GetNotificationResponse> {
        return try {
            Resource.Success(naApi.getNotification(getNotificationRequest))
        } catch (e: Exception){
            Resource.Error(e.message)
        }
    }

    override suspend fun updateNotification(updateNotificationRequest: UpdateNotificationRequest): Resource<BasicResponse> {
        return try {
            Resource.Success(naApi.updateNotification(updateNotificationRequest = updateNotificationRequest))
        }catch (e: Exception){
            Resource.Error(e.message)
        }
    }

    override suspend fun deleteNotification(deleteNotificationRequest: DeleteNotificationRequest): Resource<BasicResponse> {
        return try {
            Resource.Success(naApi.deleteNotification(deleteNotificationRequest = deleteNotificationRequest))
        }catch (e: Exception){
            Resource.Error(e.message)
        }
    }
}