package com.tinyDeveloper.na.domain.use_cases.notifications.update_notifcation_use_case

import com.tinyDeveloper.na.data.repositories.Repository
import com.tinyDeveloper.na.domain.models.request.notifications.UpdateNotificationRequest
import com.tinyDeveloper.na.domain.models.response.BasicResponse
import com.tinyDeveloper.na.utils.Resource

class UpdateNotification(private val repository: Repository) {
    suspend operator fun invoke(updateNotificationRequest: UpdateNotificationRequest): Resource<BasicResponse>{
        return repository.updateNotification(updateNotificationRequest = updateNotificationRequest)
    }
}