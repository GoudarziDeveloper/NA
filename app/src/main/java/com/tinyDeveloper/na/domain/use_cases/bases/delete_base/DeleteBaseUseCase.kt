package com.tinyDeveloper.na.domain.use_cases.bases.delete_base

import com.tinyDeveloper.na.data.repositories.Repository
import com.tinyDeveloper.na.domain.models.request.AdvanceRequest
import com.tinyDeveloper.na.domain.models.response.BasicResponse
import com.tinyDeveloper.na.utils.Resource

class DeleteBaseUseCase(private val repository: Repository) {
    suspend operator fun invoke(advanceRequest: AdvanceRequest): Resource<BasicResponse>{
        return repository.deleteBase(advanceRequest = advanceRequest)
    }
}