package com.tinyDeveloper.na.data.repositories.remote_data_surce

import com.tinyDeveloper.na.data.remote.NaApi
import com.tinyDeveloper.na.domain.models.request.AdvanceRequest
import com.tinyDeveloper.na.domain.models.request.BasicRequest
import com.tinyDeveloper.na.domain.models.request.bases.add.AddBaseRequest
import com.tinyDeveloper.na.domain.models.request.bases.update.UpdateBaseRequest
import com.tinyDeveloper.na.domain.models.response.BasicResponse
import com.tinyDeveloper.na.domain.models.response.bases.get.GetBaseResponse
import com.tinyDeveloper.na.domain.models.response.bases.get_all.GetAllBasesResponse
import com.tinyDeveloper.na.domain.repositories.BasesSource
import com.tinyDeveloper.na.utils.Resource

class BasesSourceImpl(private val naApi: NaApi): BasesSource {
    override suspend fun addBase(addBaseRequest: AddBaseRequest): Resource<BasicResponse> {
        return try {
            Resource.Success(naApi.addBase(addBaseRequest = addBaseRequest))
        } catch (e: Exception){
            Resource.Error(e.message)
        }
    }

    override suspend fun updateBase(updateBaseRequest: UpdateBaseRequest): Resource<BasicResponse> {
        return try {
            Resource.Success(naApi.updateBase(updateBaseRequest = updateBaseRequest))
        } catch (e: Exception){
            Resource.Error(e.message)
        }
    }

    override suspend fun deleteBase(advanceRequest: AdvanceRequest): Resource<BasicResponse> {
        return try {
            Resource.Success(naApi.deleteBase(advanceRequest = advanceRequest))
        } catch (e: Exception){
            Resource.Error(e.message)
        }
    }

    override suspend fun getBase(advanceRequest: AdvanceRequest): Resource<GetBaseResponse> {
        return try {
            Resource.Success(naApi.getBase(advanceRequest = advanceRequest))
        } catch (e: Exception){
            Resource.Error(e.message)
        }
    }

    override suspend fun getAllBases(basicRequest: BasicRequest): Resource<GetAllBasesResponse> {
        return try {
            Resource.Success(naApi.getAllBases(basicRequest = basicRequest))
        } catch (e: Exception){
            Resource.Error(e.message)
        }
    }
}