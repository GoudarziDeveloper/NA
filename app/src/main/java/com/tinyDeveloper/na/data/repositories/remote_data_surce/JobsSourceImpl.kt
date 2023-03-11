package com.tinyDeveloper.na.data.repositories.remote_data_surce

import com.tinyDeveloper.na.data.remote.NaApi
import com.tinyDeveloper.na.domain.models.request.AdvanceRequest
import com.tinyDeveloper.na.domain.models.request.BasicRequest
import com.tinyDeveloper.na.domain.models.request.jobs.AddJobRequest
import com.tinyDeveloper.na.domain.models.request.jobs.UpdateJobRequest
import com.tinyDeveloper.na.domain.models.response.BasicResponse
import com.tinyDeveloper.na.domain.models.response.jobs.add.AddJobResponse
import com.tinyDeveloper.na.domain.models.response.jobs.get_all.GetAllJobsResponse
import com.tinyDeveloper.na.domain.models.response.jobs.get_job.GetJobResponse
import com.tinyDeveloper.na.domain.repositories.JobsSource
import com.tinyDeveloper.na.utils.Resource
import javax.inject.Inject

class JobsSourceImpl @Inject constructor(private val naApi: NaApi): JobsSource {
    override suspend fun getAllJobs(url:String, basicRequest: BasicRequest): Resource<GetAllJobsResponse> {
        return try{
            Resource.Success(naApi.getAllJobs(url = url, basicRequest = basicRequest))
        }catch (e: Exception){
            Resource.Error(e.message)
        }
    }

    override suspend fun getJob(advanceRequest: AdvanceRequest): Resource<GetJobResponse> {
        return try {
            Resource.Success(naApi.getJob(advanceRequest = advanceRequest))
        }catch (e: Exception){
            Resource.Error(e.message)
        }
    }

    override suspend fun addJob(addJobRequest: AddJobRequest): Resource<AddJobResponse> {
        return try {
            Resource.Success(naApi.addJob(addJobRequest = addJobRequest))
        }catch (e: Exception){
            Resource.Error(e.message)
        }
    }

    override suspend fun updateJob(updateJobRequest: UpdateJobRequest): Resource<BasicResponse> {
        return try {
            Resource.Success(naApi.updateJob(updateJobRequest = updateJobRequest))
        }catch (e: Exception){
            Resource.Error(e.message)
        }
    }

    override suspend fun deleteJob(advanceRequest: AdvanceRequest): Resource<BasicResponse> {
        return try {
            Resource.Success(naApi.deleteJob(advanceRequest = advanceRequest))
        }catch (e: Exception){
            Resource.Error(e.message)
        }
    }
}