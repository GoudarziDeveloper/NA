package com.tinyDeveloper.na.domain.use_cases.bases.get_all_bases

import com.tinyDeveloper.na.data.repositories.Repository
import com.tinyDeveloper.na.domain.models.request.BasicRequest
import com.tinyDeveloper.na.domain.models.response.bases.get_all.GetAllBasesResponse
import com.tinyDeveloper.na.utils.Resource

class GetAllBasesUseCase(private val repository: Repository) {
    suspend operator fun invoke(basicRequest: BasicRequest): Resource<GetAllBasesResponse>{
        return repository.getAllBases(basicRequest = basicRequest)
    }
}