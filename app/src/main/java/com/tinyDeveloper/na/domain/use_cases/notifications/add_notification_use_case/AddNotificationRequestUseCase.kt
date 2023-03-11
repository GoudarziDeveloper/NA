package com.tinyDeveloper.na.domain.use_cases.notifications.add_notification_use_case

import com.tinyDeveloper.na.data.repositories.Repository
import com.tinyDeveloper.na.domain.models.request.notifications.AddNotificationRequest
import com.tinyDeveloper.na.domain.models.response.BasicResponse
import com.tinyDeveloper.na.utils.Resource

class AddNotification(private val repository: Repository) {
    suspend operator fun invoke(addNotificationRequest: AddNotificationRequest): Resource<BasicResponse>{
        return repository.addNotification(addNotificationRequest = addNotificationRequest)
    }
}