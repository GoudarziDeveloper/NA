package com.tinyDeveloper.na.domain.use_cases.users.get_all_users

import com.tinyDeveloper.na.data.repositories.Repository
import com.tinyDeveloper.na.domain.models.request.BasicRequest
import com.tinyDeveloper.na.domain.models.response.users.get_all.GetAllUsersResponse
import com.tinyDeveloper.na.utils.Resource

class GetAllUsersUseCase(private val repository: Repository) {
    suspend operator fun invoke(basicRequest: BasicRequest): Resource<GetAllUsersResponse>{
        return repository.getAllUsers(basicRequest = basicRequest)
    }
}