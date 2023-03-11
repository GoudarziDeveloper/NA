package com.tinyDeveloper.na.domain.use_cases.submissions.get_all

import com.tinyDeveloper.na.data.repositories.Repository
import com.tinyDeveloper.na.domain.models.request.submissions.GetAllSubmissionsRequest
import com.tinyDeveloper.na.domain.models.response.submissions.GetAllSubmissionsResponse
import com.tinyDeveloper.na.utils.Resource

class GetAllSubmissionsUseCase(private val repository: Repository) {
    suspend operator fun invoke(getAllSubmissionsRequest: GetAllSubmissionsRequest): Resource<GetAllSubmissionsResponse>{
        return repository.getAllSubmissions(getAllSubmissionsRequest = getAllSubmissionsRequest)
    }
}