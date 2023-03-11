package com.tinyDeveloper.na.domain.repositories

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
import com.tinyDeveloper.na.utils.Resource

interface SubmissionsSource {
    suspend fun getAllSubmissions(getAllSubmissionsRequest: GetAllSubmissionsRequest): Resource<GetAllSubmissionsResponse>
    suspend fun addSubmission(addSubmissionRequest: AddSubmissionRequest): Resource<AddSubmissionResponse>
    suspend fun updateSubmission(updateSubmissionRequest: UpdateSubmissionRequest): Resource<BasicResponse>
    suspend fun getSubmission(getSubmissionRequest: GetSubmissionRequest): Resource<GetSubmissionResponse>
    suspend fun getUserSubmission(advanceRequest: AdvanceRequest): Resource<GetSubmissionResponse>
}