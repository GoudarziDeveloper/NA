package com.tinyDeveloper.na.domain.use_cases.users.get_profile

import com.tinyDeveloper.na.data.repositories.Repository
import com.tinyDeveloper.na.domain.models.request.BasicRequest
import com.tinyDeveloper.na.domain.models.response.users.get_profile.GetProfileResponse
import com.tinyDeveloper.na.utils.Resource

class GetProfileUseCase(private val repository: Repository) {
    suspend operator fun invoke(basicRequest: BasicRequest): Resource<GetProfileResponse> {
        return repository.getProfile(basicRequest = basicRequest)
    }
}