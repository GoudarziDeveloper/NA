package com.tinyDeveloper.na.data.remote

import com.tinyDeveloper.na.domain.models.request.AdvanceRequest
import com.tinyDeveloper.na.domain.models.request.users.CheckRequest
import com.tinyDeveloper.na.domain.models.response.BasicResponse
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
import com.tinyDeveloper.na.utils.Constants.ADD_BASE
import com.tinyDeveloper.na.utils.Constants.ADD_FILE
import com.tinyDeveloper.na.utils.Constants.ADD_JOB
import com.tinyDeveloper.na.utils.Constants.ADD_NOTIFICATION
import com.tinyDeveloper.na.utils.Constants.ADD_SUBMISSION
import com.tinyDeveloper.na.utils.Constants.ADD_USER
import com.tinyDeveloper.na.utils.Constants.CHECK_CODE
import com.tinyDeveloper.na.utils.Constants.DELETE_BASE
import com.tinyDeveloper.na.utils.Constants.DELETE_FILE
import com.tinyDeveloper.na.utils.Constants.DELETE_JOB
import com.tinyDeveloper.na.utils.Constants.DELETE_NOTIFICATION
import com.tinyDeveloper.na.utils.Constants.DELETE_USER
import com.tinyDeveloper.na.utils.Constants.GET_ALL_BASES
import com.tinyDeveloper.na.utils.Constants.GET_ALL_SUBMISSIONS
import com.tinyDeveloper.na.utils.Constants.GET_ALL_USERS
import com.tinyDeveloper.na.utils.Constants.GET_ALL_USER_JOBS
import com.tinyDeveloper.na.utils.Constants.GET_BASE
import com.tinyDeveloper.na.utils.Constants.GET_BASE_INFO
import com.tinyDeveloper.na.utils.Constants.GET_JOB
import com.tinyDeveloper.na.utils.Constants.GET_NOTIFICATION
import com.tinyDeveloper.na.utils.Constants.GET_PROFILE
import com.tinyDeveloper.na.utils.Constants.GET_SUBMISSION
import com.tinyDeveloper.na.utils.Constants.GET_USER
import com.tinyDeveloper.na.utils.Constants.GET_USER_ROLE
import com.tinyDeveloper.na.utils.Constants.GET_USER_SUBMISSION
import com.tinyDeveloper.na.utils.Constants.LOGIN
import com.tinyDeveloper.na.utils.Constants.UPDATE_BASE
import com.tinyDeveloper.na.utils.Constants.UPDATE_BASE_INFO
import com.tinyDeveloper.na.utils.Constants.UPDATE_JOB
import com.tinyDeveloper.na.utils.Constants.UPDATE_NOTIFICATION
import com.tinyDeveloper.na.utils.Constants.UPDATE_PROFILE
import com.tinyDeveloper.na.utils.Constants.UPDATE_SUBMISSION
import com.tinyDeveloper.na.utils.Constants.UPDATE_USER
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Url

interface NaApi {
    @POST(LOGIN)
    suspend fun login(@Body basicRequest: BasicRequest): BasicResponse

    @POST(CHECK_CODE)
    suspend fun checkCode(@Body checkRequest: CheckRequest): CheckResponse

    @GET(GET_BASE_INFO)
    suspend fun getBaseInfo(): BaseInfoResponse

    @POST(GET_USER_ROLE)
    suspend fun getUserRole(@Body basicRequest: BasicRequest): RoleResponse

    @POST
    suspend fun getNotifications(@Url url: String, @Body basicRequest: BasicRequest): NotificationsResponse

    @POST(ADD_NOTIFICATION)
    suspend fun addNotification(@Body addNotificationRequest: AddNotificationRequest): BasicResponse

    @POST(GET_NOTIFICATION)
    suspend fun getNotification(@Body getNotificationRequest: GetNotificationRequest): GetNotificationResponse

    @POST(UPDATE_NOTIFICATION)
    suspend fun updateNotification(@Body updateNotificationRequest: UpdateNotificationRequest): BasicResponse

    @POST(DELETE_NOTIFICATION)
    suspend fun deleteNotification(@Body deleteNotificationRequest: DeleteNotificationRequest): BasicResponse

    @POST(GET_PROFILE)
    suspend fun getProfile(@Body basicRequest: BasicRequest): GetProfileResponse

    @POST(UPDATE_PROFILE)
    suspend fun updateProfile(@Body updateProfileRequest: UpdateProfileRequest): UpdateProfileResponse

    @POST(GET_ALL_USERS)
    suspend fun getAllUsers(@Body basicRequest: BasicRequest): GetAllUsersResponse

    @POST(DELETE_USER)
    suspend fun deleteUser(@Body basicUserRequest: BasicUserRequest): BasicResponse

    @POST(ADD_USER)
    suspend fun addUser(@Body advanceUserRequest: AdvanceUserRequest): BasicResponse

    @POST(GET_USER)
    suspend fun getUser(@Body basicUserRequest: BasicUserRequest): GetUserResponse

    @POST(UPDATE_USER)
    suspend fun updateUser(@Body advanceUserRequest: AdvanceUserRequest): BasicResponse

    @POST
    suspend fun getAllJobs(@Url url: String, @Body basicRequest: BasicRequest): GetAllJobsResponse

    @POST(GET_JOB)
    suspend fun getJob(@Body advanceRequest: AdvanceRequest): GetJobResponse

    @POST(ADD_JOB)
    suspend fun addJob(@Body addJobRequest: AddJobRequest): AddJobResponse

    @POST(ADD_FILE)
    suspend fun addFile(@Body addFileRequest: AddFileRequest): AddFileResponse

    @POST(UPDATE_JOB)
    suspend fun updateJob(@Body updateJobRequest: UpdateJobRequest): BasicResponse

    @POST(DELETE_FILE)
    suspend fun deleteFile(@Body advanceRequest: AdvanceRequest): BasicResponse

    @POST(DELETE_JOB)
    suspend fun deleteJob(@Body advanceRequest: AdvanceRequest): BasicResponse

    @POST(GET_ALL_SUBMISSIONS)
    suspend fun getAllSubmissions(@Body getAllSubmissionsRequest: GetAllSubmissionsRequest): GetAllSubmissionsResponse

    @POST(ADD_SUBMISSION)
    suspend fun addSubmission(@Body addSubmissionRequest: AddSubmissionRequest): AddSubmissionResponse

    @POST(UPDATE_SUBMISSION)
    suspend fun updateSubmission(@Body updateSubmissionsRequest: UpdateSubmissionRequest): BasicResponse

    @POST(GET_SUBMISSION)
    suspend fun getSubmission(@Body getSubmissionRequest: GetSubmissionRequest): GetSubmissionResponse

    @POST(GET_USER_SUBMISSION)
    suspend fun getUserSubmission(@Body advanceRequest: AdvanceRequest): GetSubmissionResponse

    @POST(UPDATE_BASE_INFO)
    suspend fun updateBaseInfo(@Body updateBaseInfoRequest: UpdateBaseInfoRequest): BasicResponse

    @POST(GET_ALL_BASES)
    suspend fun getAllBases(@Body basicRequest: BasicRequest): GetAllBasesResponse

    @POST(GET_BASE)
    suspend fun getBase(@Body advanceRequest: AdvanceRequest): GetBaseResponse

    @POST(DELETE_BASE)
    suspend fun deleteBase(@Body advanceRequest: AdvanceRequest): BasicResponse

    @POST(ADD_BASE)
    suspend fun addBase(@Body addBaseRequest: AddBaseRequest): BasicResponse

    @POST(UPDATE_BASE)
    suspend fun updateBase(@Body updateBaseRequest: UpdateBaseRequest): BasicResponse
}