package com.tinyDeveloper.na.domain.repositories

import com.tinyDeveloper.na.domain.models.request.AdvanceRequest
import com.tinyDeveloper.na.domain.models.request.BasicRequest
import com.tinyDeveloper.na.domain.models.request.bases.add.AddBaseRequest
import com.tinyDeveloper.na.domain.models.request.bases.update.UpdateBaseRequest
import com.tinyDeveloper.na.domain.models.response.BasicResponse
import com.tinyDeveloper.na.domain.models.response.bases.get.GetBaseResponse
import com.tinyDeveloper.na.domain.models.response.bases.get_all.GetAllBasesResponse
import com.tinyDeveloper.na.utils.Resource

interface BasesSource {
    suspend fun addBase(addBaseRequest: AddBaseRequest): Resource<BasicResponse>
    suspend fun updateBase(updateBaseRequest: UpdateBaseRequest): Resource<BasicResponse>
    suspend fun deleteBase(advanceRequest: AdvanceRequest): Resource<BasicResponse>
    suspend fun getBase(advanceRequest: AdvanceRequest): Resource<GetBaseResponse>
    suspend fun getAllBases(basicRequest: BasicRequest): Resource<GetAllBasesResponse>
}