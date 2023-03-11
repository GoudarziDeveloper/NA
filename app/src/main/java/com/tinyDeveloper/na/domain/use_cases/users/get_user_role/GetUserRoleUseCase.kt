package com.tinyDeveloper.na.domain.use_cases.users.get_user_role

import com.tinyDeveloper.na.data.repositories.Repository
import com.tinyDeveloper.na.domain.models.request.BasicRequest
import com.tinyDeveloper.na.domain.models.response.users.role.RoleResponse
import com.tinyDeveloper.na.utils.Resource

class GetUserRoleUseCase(private val repository: Repository) {
    suspend operator fun invoke(basicRequest: BasicRequest): Resource<RoleResponse>{
        return repository.getUserRole(basicRequest = basicRequest)
    }
}