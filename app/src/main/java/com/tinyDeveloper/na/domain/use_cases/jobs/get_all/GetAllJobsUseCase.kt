package com.tinyDeveloper.na.domain.use_cases.jobs.get_all

import com.tinyDeveloper.na.data.repositories.Repository
import com.tinyDeveloper.na.domain.models.request.BasicRequest
import com.tinyDeveloper.na.domain.models.response.jobs.get_all.GetAllJobsResponse
import com.tinyDeveloper.na.utils.Constants.GET_ALL_JOBS
import com.tinyDeveloper.na.utils.Resource

class GetAllJobsUseCase(private val repository: Repository) {
    suspend operator fun invoke(basicRequest: BasicRequest): Resource<GetAllJobsResponse>{
        return repository.getAllJobs(url = GET_ALL_JOBS, basicRequest = basicRequest)
    }
}