package com.tinyDeveloper.na.domain.repositories

import com.tinyDeveloper.na.domain.models.request.users.CheckRequest
import com.tinyDeveloper.na.domain.models.request.BasicRequest
import com.tinyDeveloper.na.domain.models.request.users.AdvanceUserRequest
import com.tinyDeveloper.na.domain.models.request.users.BasicUserRequest
import com.tinyDeveloper.na.domain.models.request.users.UpdateProfileRequest
import com.tinyDeveloper.na.domain.models.response.BasicResponse
import com.tinyDeveloper.na.domain.models.response.base_info.BaseInfoResponse
import com.tinyDeveloper.na.domain.models.response.users.check_code.CheckResponse
import com.tinyDeveloper.na.domain.models.response.users.get_all.GetAllUsersResponse
import com.tinyDeveloper.na.domain.models.response.users.get_profile.GetProfileResponse
import com.tinyDeveloper.na.domain.models.response.users.get_user.GetUserResponse
import com.tinyDeveloper.na.domain.models.response.users.role.RoleResponse
import com.tinyDeveloper.na.domain.models.response.users.update_profile.UpdateProfileResponse
import com.tinyDeveloper.na.utils.Resource

interface UsersSource {
    suspend fun login(basicRequest: BasicRequest): Resource<BasicResponse>
    suspend fun checkCode(checkRequest: CheckRequest): Resource<CheckResponse>
    suspend fun getUserRole(basicRequest: BasicRequest): Resource<RoleResponse>
    suspend fun getProfile(basicRequest: BasicRequest): Resource<GetProfileResponse>
    suspend fun updateProfile(updateProfileRequest: UpdateProfileRequest): Resource<UpdateProfileResponse>
    suspend fun getAllUsers(basicRequest: BasicRequest): Resource<GetAllUsersResponse>
    suspend fun deleteUser(basicUserRequest: BasicUserRequest): Resource<BasicResponse>
    suspend fun addUser(advanceUserRequest: AdvanceUserRequest): Resource<BasicResponse>
    suspend fun getUser(basicUserRequest: BasicUserRequest): Resource<GetUserResponse>
    suspend fun updateUser(advanceUserRequest: AdvanceUserRequest): Resource<BasicResponse>
}