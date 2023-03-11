package com.tinyDeveloper.na.data.repositories

import com.tinyDeveloper.na.domain.models.request.AdvanceRequest
import com.tinyDeveloper.na.domain.models.request.users.CheckRequest
import com.tinyDeveloper.na.domain.models.request.BasicRequest
import com.tinyDeveloper.na.domain.models.request.base_info.update_base_info.UpdateBaseInfoRequest
import com.tinyDeveloper.na.domain.models.request.bases.add.AddBaseRequest
import com.tinyDeveloper.na.domain.models.request.bases.update.UpdateBaseRequest
import com.tinyDeveloper.na.domain.models.request.files.AddFileRequest
import com.tinyDeveloper.na.domain.models.request.jobs.AddJobRequest
import com.tinyDeveloper.na.domain.models.request.jobs.UpdateJobRequest
import com.tinyDeveloper.na.domain.models.request.notifications.AddNotificationRequest
import com.tinyDeveloper.na.domain.models.request.notifications.DeleteNotificationRequest
import com.tinyDeveloper.na.domain.models.request.notifications.GetNotificationRequest
import com.tinyDeveloper.na.domain.models.request.notifications.UpdateNotificationRequest
import com.tinyDeveloper.na.domain.models.request.submissions.AddSubmissionRequest
import com.tinyDeveloper.na.domain.models.request.submissions.GetAllSubmissionsRequest
import com.tinyDeveloper.na.domain.models.request.submissions.GetSubmissionRequest
import com.tinyDeveloper.na.domain.models.request.submissions.UpdateSubmissionRequest
import com.tinyDeveloper.na.domain.models.request.users.AdvanceUserRequest
import com.tinyDeveloper.na.domain.models.request.users.BasicUserRequest
import com.tinyDeveloper.na.domain.models.request.users.UpdateProfileRequest
import com.tinyDeveloper.na.domain.models.response.BasicResponse
import com.tinyDeveloper.na.domain.models.response.base_info.BaseInfoResponse
import com.tinyDeveloper.na.domain.models.response.bases.get.GetBaseResponse
import com.tinyDeveloper.na.domain.models.response.bases.get_all.GetAllBasesResponse
import com.tinyDeveloper.na.domain.models.response.files.AddFileResponse
import com.tinyDeveloper.na.domain.models.response.jobs.add.AddJobResponse
import com.tinyDeveloper.na.domain.models.response.jobs.get_all.GetAllJobsResponse
import com.tinyDeveloper.na.domain.models.response.jobs.get_job.GetJobResponse
import com.tinyDeveloper.na.domain.models.response.notifications.GetNotificationResponse
import com.tinyDeveloper.na.domain.models.response.notifications.NotificationsResponse
import com.tinyDeveloper.na.domain.models.response.submissions.AddSubmissionResponse
import com.tinyDeveloper.na.domain.models.response.submissions.GetAllSubmissionsResponse
import com.tinyDeveloper.na.domain.models.response.submissions.GetSubmissionResponse
import com.tinyDeveloper.na.domain.models.response.users.check_code.CheckResponse
import com.tinyDeveloper.na.domain.models.response.users.get_all.GetAllUsersResponse
import com.tinyDeveloper.na.domain.models.response.users.get_profile.GetProfileResponse
import com.tinyDeveloper.na.domain.models.response.users.get_user.GetUserResponse
import com.tinyDeveloper.na.domain.models.response.users.role.RoleResponse
import com.tinyDeveloper.na.domain.models.response.users.update_profile.UpdateProfileResponse
import com.tinyDeveloper.na.domain.repositories.*
import com.tinyDeveloper.na.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(
    private val usersSource: UsersSource,
    private val notificationsSource: NotificationsSource,
    private val dataStore: DataStore,
    private val jobsSource: JobsSource,
    private val filesSource: FilesSource,
    private val submissionsSource: SubmissionsSource,
    private val baseInfoSource: BaseInfoSource,
    private val basesSource: BasesSource
) {
    suspend fun login(basicRequest: BasicRequest): Resource<BasicResponse>{
        return usersSource.login(basicRequest = basicRequest)
    }
    suspend fun checkCode(checkRequest: CheckRequest): Resource<CheckResponse>{
        return usersSource.checkCode(checkRequest = checkRequest)
    }

    suspend fun getBaseInfo(): Resource<BaseInfoResponse>{
        return baseInfoSource.getBaseInfo()
    }

    val info: Flow<BasicRequest> = dataStore.info

    suspend fun setInfo(info: BasicRequest){
        dataStore.setInfo(info = info)
    }

    suspend fun getUserRole(basicRequest: BasicRequest): Resource<RoleResponse>{
        return usersSource.getUserRole(basicRequest = basicRequest)
    }

    suspend fun getNotifications(url: String, basicRequest: BasicRequest): Resource<NotificationsResponse>{
        return notificationsSource.getNotifications(url = url, basicRequest = basicRequest)
    }

    suspend fun addNotification(addNotificationRequest: AddNotificationRequest): Resource<BasicResponse>{
        return notificationsSource.addNotification(addNotificationRequest = addNotificationRequest)
    }

    suspend fun getNotification(getNotificationRequest: GetNotificationRequest): Resource<GetNotificationResponse>{
        return notificationsSource.getNotification(getNotificationRequest = getNotificationRequest)
    }

    suspend fun updateNotification(updateNotificationRequest: UpdateNotificationRequest): Resource<BasicResponse>{
        return notificationsSource.updateNotification(updateNotificationRequest = updateNotificationRequest)
    }

    suspend fun deleteNotification(deleteNotificationRequest: DeleteNotificationRequest): Resource<BasicResponse>{
        return notificationsSource.deleteNotification(deleteNotificationRequest = deleteNotificationRequest)
    }

    suspend fun getProfile(basicRequest: BasicRequest): Resource<GetProfileResponse>{
        return usersSource.getProfile(basicRequest = basicRequest)
    }

    suspend fun updateProfile(updateProfileRequest: UpdateProfileRequest): Resource<UpdateProfileResponse>{
        return usersSource.updateProfile(updateProfileRequest = updateProfileRequest)
    }

    suspend fun getAllUsers(basicRequest: BasicRequest): Resource<GetAllUsersResponse>{
        return usersSource.getAllUsers(basicRequest = basicRequest)
    }

    suspend fun deleteUser(basicUserRequest: BasicUserRequest): Resource<BasicResponse>{
        return usersSource.deleteUser(basicUserRequest = basicUserRequest)
    }

    suspend fun addUser(advanceUserRequest: AdvanceUserRequest): Resource<BasicResponse>{
        return usersSource.addUser(advanceUserRequest = advanceUserRequest)
    }

    suspend fun getUser(basicUserRequest: BasicUserRequest): Resource<GetUserResponse>{
        return usersSource.getUser(basicUserRequest = basicUserRequest)
    }

    suspend fun updateUser(advanceUserRequest: AdvanceUserRequest): Resource<BasicResponse>{
        return usersSource.updateUser(advanceUserRequest = advanceUserRequest)
    }

    suspend fun getAllJobs(url: String, basicRequest: BasicRequest): Resource<GetAllJobsResponse>{
        return jobsSource.getAllJobs(url = url, basicRequest = basicRequest)
    }

    suspend fun getJob(advanceRequest: AdvanceRequest): Resource<GetJobResponse>{
        return jobsSource.getJob(advanceRequest = advanceRequest)
    }

    suspend fun addJob(addJobRequest: AddJobRequest): Resource<AddJobResponse>{
        return jobsSource.addJob(addJobRequest = addJobRequest)
    }

    suspend fun addFile(addFileRequest: AddFileRequest): Resource<AddFileResponse>{
        return filesSource.addFile(addFileRequest = addFileRequest)
    }

    suspend fun updateJob(updateJobRequest: UpdateJobRequest): Resource<BasicResponse>{
        return jobsSource.updateJob(updateJobRequest = updateJobRequest)
    }

    suspend fun deleteFile(advanceRequest: AdvanceRequest): Resource<BasicResponse>{
        return filesSource.deleteFile(advanceRequest = advanceRequest)
    }

    suspend fun deleteJob(advanceRequest: AdvanceRequest): Resource<BasicResponse>{
        return jobsSource.deleteJob(advanceRequest = advanceRequest)
    }

    suspend fun getAllSubmissions(getAllSubmissionsRequest: GetAllSubmissionsRequest): Resource<GetAllSubmissionsResponse>{
        return submissionsSource.getAllSubmissions(getAllSubmissionsRequest = getAllSubmissionsRequest)
    }

    suspend fun addSubmission(addSubmissionRequest: AddSubmissionRequest): Resource<AddSubmissionResponse>{
        return submissionsSource.addSubmission(addSubmissionRequest = addSubmissionRequest)
    }

    suspend fun updateSubmission(updateSubmissionRequest: UpdateSubmissionRequest): Resource<BasicResponse>{
        return submissionsSource.updateSubmission(updateSubmissionRequest = updateSubmissionRequest)
    }

    suspend fun getSubmission(getSubmissionRequest: GetSubmissionRequest): Resource<GetSubmissionResponse>{
        return submissionsSource.getSubmission(getSubmissionRequest = getSubmissionRequest)
    }

    suspend fun getUserSubmission(advanceRequest: AdvanceRequest): Resource<GetSubmissionResponse>{
        return submissionsSource.getUserSubmission(advanceRequest = advanceRequest)
    }

    suspend fun updateBaseInfo(updateBaseInfo: UpdateBaseInfoRequest): Resource<BasicResponse>{
        return baseInfoSource.updateBaseInfo(updateBaseInfoRequest = updateBaseInfo)
    }

    suspend fun addBase(addBaseRequest: AddBaseRequest): Resource<BasicResponse>{
        return basesSource.addBase(addBaseRequest = addBaseRequest)
    }

    suspend fun updateBase(updateBaseRequest: UpdateBaseRequest): Resource<BasicResponse>{
        return basesSource.updateBase(updateBaseRequest = updateBaseRequest)
    }

    suspend fun deleteBase(advanceRequest: AdvanceRequest): Resource<BasicResponse>{
        return basesSource.deleteBase(advanceRequest = advanceRequest)
    }

    suspend fun getBase(advanceRequest: AdvanceRequest): Resource<GetBaseResponse>{
        return basesSource.getBase(advanceRequest = advanceRequest)
    }

    suspend fun getAllBases(basicRequest: BasicRequest): Resource<GetAllBasesResponse>{
        return basesSource.getAllBases(basicRequest = basicRequest)
    }
}