package com.tinyDeveloper.na.data.repositories.remote_data_surce

import com.tinyDeveloper.na.data.remote.NaApi
import com.tinyDeveloper.na.domain.models.request.base_info.update_base_info.UpdateBaseInfoRequest
import com.tinyDeveloper.na.domain.models.response.BasicResponse
import com.tinyDeveloper.na.domain.models.response.base_info.BaseInfoResponse
import com.tinyDeveloper.na.domain.repositories.BaseInfoSource
import com.tinyDeveloper.na.utils.Resource

class BaseInfoSourceImpl(private val naApi: NaApi): BaseInfoSource {
    override suspend fun getBaseInfo(): Resource<BaseInfoResponse> {
        return try {
            Resource.Success(naApi.getBaseInfo())
        } catch (e: Exception){
            Resource.Error(e.message)
        }
    }

    override suspend fun updateBaseInfo(updateBaseInfoRequest: UpdateBaseInfoRequest): Resource<BasicResponse> {
        return try {
            Resource.Success(naApi.updateBaseInfo(updateBaseInfoRequest = updateBaseInfoRequest))
        } catch (e: Exception){
            Resource.Error(e.message)
        }
    }
}