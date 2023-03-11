package com.tinyDeveloper.na.domain.use_cases.notifications.get_notification_use_case

import com.tinyDeveloper.na.data.repositories.Repository
import com.tinyDeveloper.na.domain.models.request.notifications.GetNotificationRequest
import com.tinyDeveloper.na.domain.models.response.notifications.GetNotificationResponse
import com.tinyDeveloper.na.utils.Resource

class GetNotification(private val repository: Repository) {
    suspend operator fun invoke(getNotificationRequest: GetNotificationRequest): Resource<GetNotificationResponse>{
        return repository.getNotification(getNotificationRequest = getNotificationRequest)
    }
}