package com.tinyDeveloper.na.domain.use_cases.jobs.get_all_not_worked_jobs

import com.tinyDeveloper.na.data.repositories.Repository
import com.tinyDeveloper.na.domain.models.request.BasicRequest
import com.tinyDeveloper.na.domain.models.response.jobs.get_all.GetAllJobsResponse
import com.tinyDeveloper.na.utils.Constants
import com.tinyDeveloper.na.utils.Resource

class GetAllNotWorkedJobsUseCase(private val repository: Repository) {
    suspend operator fun invoke(basicRequest: BasicRequest): Resource<GetAllJobsResponse> {
        return repository.getAllJobs(url = Constants.GET_ALL_NOT_WORKED_JOBS, basicRequest = basicRequest)
    }
}