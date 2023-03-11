package com.tinyDeveloper.na.domain.use_cases.jobs.get

import com.tinyDeveloper.na.data.repositories.Repository
import com.tinyDeveloper.na.domain.models.request.AdvanceRequest
import com.tinyDeveloper.na.domain.models.response.jobs.get_job.GetJobResponse
import com.tinyDeveloper.na.utils.Resource

class GetJobUseCase(private val repository: Repository) {
    suspend operator fun invoke(advanceRequest: AdvanceRequest): Resource<GetJobResponse>{
        return repository.getJob(advanceRequest = advanceRequest)
    }
}