package com.tinyDeveloper.na.domain.use_cases.submissions.get_user_submission

import com.tinyDeveloper.na.data.repositories.Repository
import com.tinyDeveloper.na.domain.models.request.AdvanceRequest
import com.tinyDeveloper.na.domain.models.request.BasicRequest
import com.tinyDeveloper.na.domain.models.response.submissions.GetSubmissionResponse
import com.tinyDeveloper.na.utils.Resource

class GetUserSubmissionUseCase(private val repository: Repository) {
    suspend operator fun invoke(advanceRequest: AdvanceRequest): Resource<GetSubmissionResponse>{
        return repository.getUserSubmission(advanceRequest = advanceRequest)
    }
}