package com.tinyDeveloper.na.domain.use_cases.bases.add_base

import com.tinyDeveloper.na.data.repositories.Repository
import com.tinyDeveloper.na.domain.models.request.bases.add.AddBaseRequest
import com.tinyDeveloper.na.domain.models.response.BasicResponse
import com.tinyDeveloper.na.utils.Resource

class AddBaseUseCase(private val repository: Repository){
    suspend operator fun invoke(addBaseRequest: AddBaseRequest): Resource<BasicResponse>{
        return repository.addBase(addBaseRequest = addBaseRequest)
    }
}