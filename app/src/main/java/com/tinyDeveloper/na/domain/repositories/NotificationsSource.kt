package com.tinyDeveloper.na.domain.repositories

import com.tinyDeveloper.na.domain.models.request.BasicRequest
import com.tinyDeveloper.na.domain.models.request.notifications.AddNotificationRequest
import com.tinyDeveloper.na.domain.models.request.notifications.DeleteNotificationRequest
import com.tinyDeveloper.na.domain.models.request.notifications.GetNotificationRequest
import com.tinyDeveloper.na.domain.models.request.notifications.UpdateNotificationRequest
import com.tinyDeveloper.na.domain.models.response.BasicResponse
import com.tinyDeveloper.na.domain.models.response.notifications.GetNotificationResponse
import com.tinyDeveloper.na.domain.models.response.notifications.NotificationsResponse
import com.tinyDeveloper.na.utils.Resource

interface NotificationsSource {
    suspend fun getNotifications(url: String, basicRequest: BasicRequest): Resource<NotificationsResponse>
    suspend fun addNotification(addNotificationRequest: AddNotificationRequest): Resource<BasicResponse>
    suspend fun getNotification(getNotificationRequest: GetNotificationRequest): Resource<GetNotificationResponse>
    suspend fun updateNotification(updateNotificationRequest: UpdateNotificationRequest): Resource<BasicResponse>
    suspend fun deleteNotification(deleteNotificationRequest: DeleteNotificationRequest): Resource<BasicResponse>
}