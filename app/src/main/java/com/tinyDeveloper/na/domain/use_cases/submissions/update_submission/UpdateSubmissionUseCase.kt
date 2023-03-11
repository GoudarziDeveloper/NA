package com.tinyDeveloper.na.domain.use_cases.submissions.update_submission

import com.tinyDeveloper.na.data.repositories.Repository
import com.tinyDeveloper.na.domain.models.request.submissions.UpdateSubmissionRequest
import com.tinyDeveloper.na.domain.models.response.BasicResponse
import com.tinyDeveloper.na.utils.Resource

class UpdateSubmissionUseCase(private val repository: Repository) {
    suspend operator fun invoke(updateSubmissionRequest: UpdateSubmissionRequest): Resource<BasicResponse>{
        return repository.updateSubmission(updateSubmissionRequest = updateSubmissionRequest)
    }
}