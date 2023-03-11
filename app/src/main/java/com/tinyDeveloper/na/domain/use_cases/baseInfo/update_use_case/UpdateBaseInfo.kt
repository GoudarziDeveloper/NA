package com.tinyDeveloper.na.domain.use_cases.baseInfo.update_use_case

import com.tinyDeveloper.na.data.repositories.Repository
import com.tinyDeveloper.na.domain.models.request.base_info.update_base_info.UpdateBaseInfoRequest
import com.tinyDeveloper.na.domain.models.response.BasicResponse
import com.tinyDeveloper.na.utils.Resource

class UpdateBaseInfo(private val repository: Repository) {
    suspend operator fun invoke(updateBaseInfo: UpdateBaseInfoRequest): Resource<BasicResponse>{
        return repository.updateBaseInfo(updateBaseInfo = updateBaseInfo)
    }
}