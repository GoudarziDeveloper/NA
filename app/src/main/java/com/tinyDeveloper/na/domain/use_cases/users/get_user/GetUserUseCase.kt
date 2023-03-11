package com.tinyDeveloper.na.domain.use_cases.users.get_user

import com.tinyDeveloper.na.data.repositories.Repository
import com.tinyDeveloper.na.domain.models.request.users.BasicUserRequest
import com.tinyDeveloper.na.domain.models.response.users.get_user.GetUserResponse
import com.tinyDeveloper.na.utils.Resource

class GetUserUseCase(private val repository: Repository) {
    suspend operator fun invoke(basicUserRequest: BasicUserRequest): Resource<GetUserResponse>{
        return repository.getUser(basicUserRequest = basicUserRequest)
    }
}