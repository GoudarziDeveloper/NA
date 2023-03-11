package com.tinyDeveloper.na.domain.use_cases.data_store

import com.tinyDeveloper.na.data.repositories.Repository
import com.tinyDeveloper.na.domain.models.request.BasicRequest
import kotlinx.coroutines.flow.Flow

class GetInfoUseCase(private val repository: Repository) {
    suspend operator fun invoke(): Flow<BasicRequest> {
        return repository.info
    }
}