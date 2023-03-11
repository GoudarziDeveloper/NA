package com.tinyDeveloper.na.domain.use_cases.notifications.personal.get_notifications

import com.tinyDeveloper.na.data.repositories.Repository
import com.tinyDeveloper.na.domain.models.request.BasicRequest
import com.tinyDeveloper.na.domain.models.response.notifications.NotificationsResponse
import com.tinyDeveloper.na.utils.Constants.GET_PERSONAL_NOTIFICATIONS
import com.tinyDeveloper.na.utils.Resource

class GetPrivateNotifications(private val repository: Repository) {
    suspend operator fun invoke(basicRequest: BasicRequest): Resource<NotificationsResponse>{
        return repository.getNotifications(
            url = GET_PERSONAL_NOTIFICATIONS,
            basicRequest = basicRequest
        )
    }
}