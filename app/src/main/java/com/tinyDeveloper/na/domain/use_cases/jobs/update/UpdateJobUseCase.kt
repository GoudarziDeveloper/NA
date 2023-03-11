package com.tinyDeveloper.na.domain.use_cases.jobs.update

import com.tinyDeveloper.na.data.repositories.Repository
import com.tinyDeveloper.na.domain.models.request.jobs.UpdateJobRequest
import com.tinyDeveloper.na.domain.models.response.BasicResponse
import com.tinyDeveloper.na.utils.Resource

class UpdateJobUseCase(private val repository: Repository) {
    suspend operator fun invoke(updateJobRequest: UpdateJobRequest): Resource<BasicResponse>{
        return repository.updateJob(updateJobRequest = updateJobRequest)
    }
}