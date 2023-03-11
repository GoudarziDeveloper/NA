package com.tinyDeveloper.na.domain.repositories

import com.tinyDeveloper.na.domain.models.request.BasicRequest
import kotlinx.coroutines.flow.Flow

interface DataStore {
    suspend fun setInfo(info: BasicRequest)
    val info: Flow<BasicRequest>
}