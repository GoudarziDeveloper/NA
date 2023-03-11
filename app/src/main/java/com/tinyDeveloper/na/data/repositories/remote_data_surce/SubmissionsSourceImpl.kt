package com.tinyDeveloper.na.data.repositories.remote_data_surce

import com.tinyDeveloper.na.data.remote.NaApi
import com.tinyDeveloper.na.domain.models.request.AdvanceRequest
import com.tinyDeveloper.na.domain.models.request.BasicRequest
import com.tinyDeveloper.na.domain.models.request.submissions.AddSubmissionRequest
import com.tinyDeveloper.na.domain.models.request.submissions.GetAllSubmissionsRequest
import com.tinyDeveloper.na.domain.models.request.submissions.GetSubmissionRequest
import com.tinyDeveloper.na.domain.models.request.submissions.UpdateSubmissionRequest
import com.tinyDeveloper.na.domain.models.response.BasicResponse
import com.tinyDeveloper.na.domain.models.response.submissions.AddSubmissionResponse
import com.tinyDeveloper.na.domain.models.response.submissions.GetAllSubmissionsResponse
import com.tinyDeveloper.na.domain.models.response.submissions.GetSubmissionResponse
import com.tinyDeveloper.na.domain.repositories.SubmissionsSource
import com.tinyDeveloper.na.utils.Resource
import javax.inject.Inject

class SubmissionsSourceImpl @Inject constructor(private val naApi: NaApi): SubmissionsSource {
    override suspend fun getAllSubmissions(getAllSubmissionsRequest: GetAllSubmissionsRequest): Resource<GetAllSubmissionsResponse> {
        return try {
            Resource.Success(naApi.getAllSubmissions(getAllSubmissionsRequest = getAllSubmissionsRequest))
        }catch (e: Exception){
            Resource.Error(e.message)
        }
    }

    override suspend fun addSubmission(addSubmissionRequest: AddSubmissionRequest): Resource<AddSubmissionResponse> {
        return try {
            Resource.Success(naApi.addSubmission(addSubmissionRequest = addSubmissionRequest))
        }catch (e: Exception){
            Resource.Error(e.message)
        }
    }

    override suspend fun updateSubmission(updateSubmissionRequest: UpdateSubmissionRequest): Resource<BasicResponse> {
        return try {
            Resource.Success(naApi.updateSubmission(updateSubmissionsRequest = updateSubmissionRequest))
        }catch (e: Exception){
            Resource.Error(e.message)
        }
    }

    override suspend fun getSubmission(getSubmissionRequest: GetSubmissionRequest): Resource<GetSubmissionResponse> {
        return try {
            Resource.Success(naApi.getSubmission(getSubmissionRequest = getSubmissionRequest))
        }catch (e: Exception){
            Resource.Error(e.message)
        }
    }

    override suspend fun getUserSubmission(advanceRequest: AdvanceRequest): Resource<GetSubmissionResponse> {
        return try {
            Resource.Success(naApi.getUserSubmission(advanceRequest = advanceRequest))
        }catch (e: Exception){
            Resource.Error(e.message)
        }
    }
}