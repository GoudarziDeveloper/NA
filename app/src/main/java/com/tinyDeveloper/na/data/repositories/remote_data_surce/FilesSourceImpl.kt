package com.tinyDeveloper.na.data.repositories.remote_data_surce

import com.tinyDeveloper.na.data.remote.NaApi
import com.tinyDeveloper.na.domain.models.request.AdvanceRequest
import com.tinyDeveloper.na.domain.models.request.files.AddFileRequest
import com.tinyDeveloper.na.domain.models.response.BasicResponse
import com.tinyDeveloper.na.domain.models.response.files.AddFileResponse
import com.tinyDeveloper.na.domain.repositories.FilesSource
import com.tinyDeveloper.na.utils.Resource
import javax.inject.Inject

class FilesSourceImpl @Inject constructor(private val naApi: NaApi): FilesSource{
    override suspend fun addFile(addFileRequest: AddFileRequest): Resource<AddFileResponse> {
        return try {
            Resource.Success(naApi.addFile(addFileRequest = addFileRequest))
        }catch (e: Exception) {
            Resource.Error(e.message)
        }
    }

    override suspend fun deleteFile(advanceRequest: AdvanceRequest): Resource<BasicResponse> {
        return try{
            Resource.Success(naApi.deleteFile(advanceRequest = advanceRequest))
        }catch (e: Exception){
            Resource.Error(e.message)
        }
    }
}