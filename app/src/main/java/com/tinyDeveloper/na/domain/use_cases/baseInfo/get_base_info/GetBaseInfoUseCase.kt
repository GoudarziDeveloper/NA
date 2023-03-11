package com.tinyDeveloper.na.domain.use_cases.baseInfo.get_base_info

import com.tinyDeveloper.na.data.repositories.Repository
import com.tinyDeveloper.na.domain.models.response.base_info.BaseInfoResponse
import com.tinyDeveloper.na.utils.Resource

class GetBaseInfoUseCase(private val repository: Repository) {
    suspend operator fun invoke(): Resource<BaseInfoResponse>{
        return repository.getBaseInfo()
    }
}