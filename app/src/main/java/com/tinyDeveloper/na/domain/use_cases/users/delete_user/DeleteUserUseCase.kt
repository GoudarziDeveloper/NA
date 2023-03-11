package com.tinyDeveloper.na.domain.use_cases.users.delete_user

import com.tinyDeveloper.na.data.repositories.Repository
import com.tinyDeveloper.na.domain.models.request.users.BasicUserRequest
import com.tinyDeveloper.na.domain.models.response.BasicResponse
import com.tinyDeveloper.na.utils.Resource

class DeleteUserUseCase(private val repository: Repository) {
    suspend operator fun invoke(basicUserRequest: BasicUserRequest): Resource<BasicResponse>{
        return repository.deleteUser(basicUserRequest = basicUserRequest)
    }
}