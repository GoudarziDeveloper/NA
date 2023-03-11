package com.tinyDeveloper.na.domain.repositories

import com.tinyDeveloper.na.domain.models.request.AdvanceRequest
import com.tinyDeveloper.na.domain.models.request.files.AddFileRequest
import com.tinyDeveloper.na.domain.models.response.BasicResponse
import com.tinyDeveloper.na.domain.models.response.files.AddFileResponse
import com.tinyDeveloper.na.utils.Resource

interface FilesSource {
    suspend fun addFile(addFileRequest: AddFileRequest): Resource<AddFileResponse>
    suspend fun deleteFile(advanceRequest: AdvanceRequest): Resource<BasicResponse>
}