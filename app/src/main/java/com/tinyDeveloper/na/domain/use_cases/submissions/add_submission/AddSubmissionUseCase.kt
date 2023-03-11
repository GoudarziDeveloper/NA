package com.tinyDeveloper.na.domain.use_cases.submissions.add_submission

import com.tinyDeveloper.na.data.repositories.Repository
import com.tinyDeveloper.na.domain.models.request.submissions.AddSubmissionRequest
import com.tinyDeveloper.na.domain.models.response.submissions.AddSubmissionResponse
import com.tinyDeveloper.na.utils.Resource

class AddSubmissionUseCase(private val repository: Repository) {
    suspend operator fun invoke(addSubmissionsRequest: AddSubmissionRequest): Resource<AddSubmissionResponse>{
        return repository.addSubmission(addSubmissionRequest = addSubmissionsRequest)
    }
}