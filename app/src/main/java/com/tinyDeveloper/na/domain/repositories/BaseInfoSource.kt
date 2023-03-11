package com.tinyDeveloper.na.domain.repositories

import com.tinyDeveloper.na.domain.models.request.base_info.update_base_info.UpdateBaseInfoRequest
import com.tinyDeveloper.na.domain.models.response.BasicResponse
import com.tinyDeveloper.na.domain.models.response.base_info.BaseInfoResponse
import com.tinyDeveloper.na.utils.Resource

interface BaseInfoSource {
    suspend fun getBaseInfo(): Resource<BaseInfoResponse>
    suspend fun updateBaseInfo(updateBaseInfoRequest: UpdateBaseInfoRequest): Resource<BasicResponse>
}