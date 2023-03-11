package com.tinyDeveloper.na.domain.use_cases.users.add_user

import com.tinyDeveloper.na.data.repositories.Repository
import com.tinyDeveloper.na.domain.models.request.users.AdvanceUserRequest
import com.tinyDeveloper.na.domain.models.response.BasicResponse
import com.tinyDeveloper.na.utils.Resource

class AddUserUserCase(private val repository: Repository) {
    suspend operator fun invoke(advanceUserRequest: AdvanceUserRequest): Resource<BasicResponse>{
        return repository.addUser(advanceUserRequest = advanceUserRequest)
    }
}