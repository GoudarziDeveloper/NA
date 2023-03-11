package com.tinyDeveloper.na.domain.use_cases.users.update_profile

import com.tinyDeveloper.na.data.repositories.Repository
import com.tinyDeveloper.na.domain.models.request.users.UpdateProfileRequest
import com.tinyDeveloper.na.domain.models.response.BasicResponse
import com.tinyDeveloper.na.domain.models.response.users.update_profile.UpdateProfileResponse
import com.tinyDeveloper.na.utils.Resource

class UpdateProfileUseCase(private val repository: Repository) {
    suspend operator fun invoke(updateProfileRequest: UpdateProfileRequest): Resource<UpdateProfileResponse>{
        return repository.updateProfile(updateProfileRequest = updateProfileRequest)
    }
}