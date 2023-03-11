package com.tinyDeveloper.na.domain.use_cases.bases.update_base

import com.tinyDeveloper.na.data.repositories.Repository
import com.tinyDeveloper.na.domain.models.request.bases.update.UpdateBaseRequest
import com.tinyDeveloper.na.domain.models.response.BasicResponse
import com.tinyDeveloper.na.utils.Resource

class UpdateBaseUseCase(private val repository: Repository) {
    suspend operator fun invoke(updateBaseRequest: UpdateBaseRequest): Resource<BasicResponse>{
        return repository.updateBase(updateBaseRequest = updateBaseRequest)
    }
}