package com.tinyDeveloper.na.domain.use_cases.jobs.add

import com.tinyDeveloper.na.data.repositories.Repository
import com.tinyDeveloper.na.domain.models.request.jobs.AddJobRequest
import com.tinyDeveloper.na.domain.models.response.jobs.add.AddJobResponse
import com.tinyDeveloper.na.utils.Resource

class AddJobUseCase(private val repository: Repository) {
    suspend operator fun invoke(addJobRequest: AddJobRequest): Resource<AddJobResponse>{
        return repository.addJob(addJobRequest = addJobRequest)
    }
}