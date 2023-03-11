package com.tinyDeveloper.na.domain.use_cases.bases.get_base

import com.tinyDeveloper.na.data.repositories.Repository
import com.tinyDeveloper.na.domain.models.request.AdvanceRequest
import com.tinyDeveloper.na.domain.models.response.bases.get.GetBaseResponse
import com.tinyDeveloper.na.utils.Resource

class GetBasesUseCase(private val repository: Repository) {
    suspend operator fun invoke(advanceRequest: AdvanceRequest): Resource<GetBaseResponse>{
        return repository.getBase(advanceRequest = advanceRequest)
    }
}