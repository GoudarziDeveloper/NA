package com.tinyDeveloper.na.data.repositories.remote_data_surce

import com.tinyDeveloper.na.data.remote.NaApi
import com.tinyDeveloper.na.domain.models.request.users.CheckRequest
import com.tinyDeveloper.na.domain.models.response.BasicResponse
import com.tinyDeveloper.na.domain.models.request.BasicRequest
import com.tinyDeveloper.na.domain.models.request.users.AdvanceUserRequest
import com.tinyDeveloper.na.domain.models.request.users.BasicUserRequest
import com.tinyDeveloper.na.domain.models.request.users.UpdateProfileRequest
import com.tinyDeveloper.na.domain.models.response.base_info.BaseInfoResponse
import com.tinyDeveloper.na.domain.models.response.users.check_code.CheckResponse
import com.tinyDeveloper.na.domain.models.response.users.get_all.GetAllUsersResponse
import com.tinyDeveloper.na.domain.models.response.users.get_profile.GetProfileResponse
import com.tinyDeveloper.na.domain.models.response.users.get_user.GetUserResponse
import com.tinyDeveloper.na.domain.models.response.users.role.RoleResponse
import com.tinyDeveloper.na.domain.models.response.users.update_profile.UpdateProfileResponse
import com.tinyDeveloper.na.domain.repositories.UsersSource
import com.tinyDeveloper.na.utils.Resource

class UsersSourceImpl(private val naApi: NaApi): UsersSource {
    override suspend fun login(basicRequest: BasicRequest): Resource<BasicResponse> {
        return try{
            Resource.Success(naApi.login(basicRequest = basicRequest))
        } catch(e: Exception){
            println(e.message)
            Resource.Error(e.message)
        }
    }

    override suspend fun checkCode(checkRequest: CheckRequest): Resource<CheckResponse> {
        return try {
            Resource.Success(naApi.checkCode(checkRequest = checkRequest))
        } catch (e: Exception){
            Resource.Error(e.message)
        }
    }

    override suspend fun getUserRole(basicRequest: BasicRequest): Resource<RoleResponse> {
        return try{
            Resource.Success(naApi.getUserRole(basicRequest = basicRequest))
        } catch(e: Exception){
            println(e.message)
            Resource.Error(e.message)
        }
    }

    override suspend fun getProfile(basicRequest: BasicRequest): Resource<GetProfileResponse> {
        return try {
            Resource.Success(naApi.getProfile(basicRequest = basicRequest))
        } catch (e:Exception){
            Resource.Error(e.message)
        }
    }

    override suspend fun updateProfile(updateProfileRequest: UpdateProfileRequest): Resource<UpdateProfileResponse> {
        return try {
            Resource.Success(naApi.updateProfile(updateProfileRequest = updateProfileRequest))
        } catch (e: Exception){
            Resource.Error(e.message)
        }
    }

    override suspend fun getAllUsers(basicRequest: BasicRequest): Resource<GetAllUsersResponse> {
        return try {
            Resource.Success(naApi.getAllUsers(basicRequest = basicRequest))
        }catch (e: Exception){
            Resource.Error(e.message)
        }
    }

    override suspend fun deleteUser(basicUserRequest: BasicUserRequest): Resource<BasicResponse> {
        return try {
            Resource.Success(naApi.deleteUser(basicUserRequest = basicUserRequest))
        }catch (e: Exception){
           Resource.Error(e.message)
        }
    }

    override suspend fun addUser(advanceUserRequest: AdvanceUserRequest): Resource<BasicResponse> {
        return try {
            Resource.Success(naApi.addUser(advanceUserRequest = advanceUserRequest))
        }catch (e: Exception){
            Resource.Error(e.message)
        }
    }

    override suspend fun getUser(basicUserRequest: BasicUserRequest): Resource<GetUserResponse> {
        return try {
            Resource.Success(naApi.getUser(basicUserRequest = basicUserRequest))
        }catch (e: Exception){
            Resource.Error(e.message)
        }
    }

    override suspend fun updateUser(advanceUserRequest: AdvanceUserRequest): Resource<BasicResponse> {
        return try {
            Resource.Success(naApi.updateUser(advanceUserRequest = advanceUserRequest))
        }catch (e: Exception){
            Resource.Error(e.message)
        }
    }
}