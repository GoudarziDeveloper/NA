package com.tinyDeveloper.na.domain.use_cases.notifications.delete_notification_use_case

import com.tinyDeveloper.na.data.repositories.Repository
import com.tinyDeveloper.na.domain.models.request.notifications.DeleteNotificationRequest
import com.tinyDeveloper.na.domain.models.response.BasicResponse
import com.tinyDeveloper.na.utils.Resource

class DeleteNotification(private val repository: Repository) {
    suspend operator fun invoke(deleteNotificationRequest: DeleteNotificationRequest): Resource<BasicResponse>{
        return repository.deleteNotification(deleteNotificationRequest = deleteNotificationRequest)
    }
}