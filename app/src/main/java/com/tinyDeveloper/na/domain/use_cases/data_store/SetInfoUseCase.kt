package com.tinyDeveloper.na.domain.use_cases.data_store

import com.tinyDeveloper.na.data.repositories.Repository
import com.tinyDeveloper.na.domain.models.request.BasicRequest

class SetInfoUseCase(private val repository: Repository) {
    suspend operator fun invoke(info: BasicRequest){
        repository.setInfo(info = info)
    }
}