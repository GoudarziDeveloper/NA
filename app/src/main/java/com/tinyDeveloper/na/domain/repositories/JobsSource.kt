package com.tinyDeveloper.na.domain.repositories

import com.tinyDeveloper.na.domain.models.request.AdvanceRequest
import com.tinyDeveloper.na.domain.models.request.BasicRequest
import com.tinyDeveloper.na.domain.models.request.jobs.AddJobRequest
import com.tinyDeveloper.na.domain.models.request.jobs.UpdateJobRequest
import com.tinyDeveloper.na.domain.models.response.BasicResponse
import com.tinyDeveloper.na.domain.models.response.jobs.add.AddJobResponse
import com.tinyDeveloper.na.domain.models.response.jobs.get_all.GetAllJobsResponse
import com.tinyDeveloper.na.domain.models.response.jobs.get_job.GetJobResponse
import com.tinyDeveloper.na.utils.Resource

interface JobsSource {
    suspend fun getAllJobs(url: String, basicRequest: BasicRequest): Resource<GetAllJobsResponse>
    suspend fun getJob(advanceRequest: AdvanceRequest): Resource<GetJobResponse>
    suspend fun addJob(addJobRequest: AddJobRequest): Resource<AddJobResponse>
    suspend fun updateJob(updateJobRequest: UpdateJobRequest): Resource<BasicResponse>
    suspend fun deleteJob(advanceRequest: AdvanceRequest): Resource<BasicResponse>
}