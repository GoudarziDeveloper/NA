package com.tinyDeveloper.na.domain.use_cases.files.add_file

import com.tinyDeveloper.na.data.repositories.Repository
import com.tinyDeveloper.na.domain.models.request.files.AddFileRequest
import com.tinyDeveloper.na.domain.models.response.files.AddFileResponse
import com.tinyDeveloper.na.utils.Resource

class AddFileUseCase(private val repository: Repository) {
    suspend operator fun invoke(addFileRequest: AddFileRequest): Resource<AddFileResponse>{
        return repository.addFile(addFileRequest = addFileRequest)
    }
}