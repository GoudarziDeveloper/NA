package com.tinyDeveloper.na.domain.use_cases.submissions.get_submission

import com.tinyDeveloper.na.data.repositories.Repository
import com.tinyDeveloper.na.domain.models.request.submissions.GetSubmissionRequest
import com.tinyDeveloper.na.domain.models.response.submissions.GetSubmissionResponse
import com.tinyDeveloper.na.utils.Resource

class GetSubmissionUseCase(private val repository: Repository) {
    suspend operator fun invoke(getSubmissionRequest: GetSubmissionRequest): Resource<GetSubmissionResponse> {
        return repository.getSubmission(getSubmissionRequest = getSubmissionRequest)
    }
}