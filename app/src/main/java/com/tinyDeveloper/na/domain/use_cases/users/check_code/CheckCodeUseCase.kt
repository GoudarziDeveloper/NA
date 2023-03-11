package com.tinyDeveloper.na.domain.use_cases.users.check_code

import com.tinyDeveloper.na.data.repositories.Repository
import com.tinyDeveloper.na.domain.models.request.users.CheckRequest
import com.tinyDeveloper.na.domain.models.response.users.check_code.CheckResponse
import com.tinyDeveloper.na.utils.Resource

class CheckCodeUseCase(private val repository: Repository) {
    suspend operator fun invoke(checkRequest: CheckRequest): Resource<CheckResponse>{
        return repository.checkCode(checkRequest = checkRequest)
    }
}