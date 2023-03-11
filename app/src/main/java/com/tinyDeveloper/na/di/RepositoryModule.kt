package com.tinyDeveloper.na.di

import com.tinyDeveloper.na.data.repositories.Repository
import com.tinyDeveloper.na.domain.use_cases.UseCases
import com.tinyDeveloper.na.domain.use_cases.baseInfo.get_base_info.GetBaseInfoUseCase
import com.tinyDeveloper.na.domain.use_cases.baseInfo.update_use_case.UpdateBaseInfo
import com.tinyDeveloper.na.domain.use_cases.bases.add_base.AddBaseUseCase
import com.tinyDeveloper.na.domain.use_cases.bases.delete_base.DeleteBaseUseCase
import com.tinyDeveloper.na.domain.use_cases.bases.get_all_bases.GetAllBasesUseCase
import com.tinyDeveloper.na.domain.use_cases.bases.get_base.GetBasesUseCase
import com.tinyDeveloper.na.domain.use_cases.bases.update_base.UpdateBaseUseCase
import com.tinyDeveloper.na.domain.use_cases.data_store.GetInfoUseCase
import com.tinyDeveloper.na.domain.use_cases.data_store.SetInfoUseCase
import com.tinyDeveloper.na.domain.use_cases.files.add_file.AddFileUseCase
import com.tinyDeveloper.na.domain.use_cases.files.delete_file.DeleteFileUseCase
import com.tinyDeveloper.na.domain.use_cases.jobs.add.AddJobUseCase
import com.tinyDeveloper.na.domain.use_cases.jobs.delete.DeleteJobUseCase
import com.tinyDeveloper.na.domain.use_cases.jobs.get.GetJobUseCase
import com.tinyDeveloper.na.domain.use_cases.jobs.get_all.GetAllJobsUseCase
import com.tinyDeveloper.na.domain.use_cases.jobs.get_all_active_jobs.GetAllActiveJobsUseCase
import com.tinyDeveloper.na.domain.use_cases.jobs.get_all_not_worked_jobs.GetAllNotWorkedJobsUseCase
import com.tinyDeveloper.na.domain.use_cases.jobs.get_all_user_jobs.GetAllUserJobsUseCase
import com.tinyDeveloper.na.domain.use_cases.jobs.update.UpdateJobUseCase
import com.tinyDeveloper.na.domain.use_cases.notifications.add_notification_use_case.AddNotification
import com.tinyDeveloper.na.domain.use_cases.notifications.common.get_all_notiifcations.GetAllCommonNotifications
import com.tinyDeveloper.na.domain.use_cases.notifications.common.get_notifications.GetCommonNotifications
import com.tinyDeveloper.na.domain.use_cases.notifications.delete_notification_use_case.DeleteNotification
import com.tinyDeveloper.na.domain.use_cases.notifications.get_notification_use_case.GetNotification
import com.tinyDeveloper.na.domain.use_cases.notifications.personal.get_all_notifications.GetAllPrivateNotifications
import com.tinyDeveloper.na.domain.use_cases.notifications.personal.get_notifications.GetPrivateNotifications
import com.tinyDeveloper.na.domain.use_cases.notifications.update_notifcation_use_case.UpdateNotification
import com.tinyDeveloper.na.domain.use_cases.submissions.add_submission.AddSubmissionUseCase
import com.tinyDeveloper.na.domain.use_cases.submissions.get_all.GetAllSubmissionsUseCase
import com.tinyDeveloper.na.domain.use_cases.submissions.get_submission.GetSubmissionUseCase
import com.tinyDeveloper.na.domain.use_cases.submissions.get_user_submission.GetUserSubmissionUseCase
import com.tinyDeveloper.na.domain.use_cases.submissions.update_submission.UpdateSubmissionUseCase
import com.tinyDeveloper.na.domain.use_cases.users.add_user.AddUserUserCase
import com.tinyDeveloper.na.domain.use_cases.users.check_code.CheckCodeUseCase
import com.tinyDeveloper.na.domain.use_cases.users.delete_user.DeleteUserUseCase
import com.tinyDeveloper.na.domain.use_cases.users.get_all_users.GetAllUsersUseCase
import com.tinyDeveloper.na.domain.use_cases.users.get_profile.GetProfileUseCase
import com.tinyDeveloper.na.domain.use_cases.users.get_user.GetUserUseCase
import com.tinyDeveloper.na.domain.use_cases.users.get_user_role.GetUserRoleUseCase
import com.tinyDeveloper.na.domain.use_cases.users.login.LoginUseCase
import com.tinyDeveloper.na.domain.use_cases.users.update_profile.UpdateProfileUseCase
import com.tinyDeveloper.na.domain.use_cases.users.update_user.UpdateUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideUseCases(repository: Repository): UseCases{
        return UseCases(
            loginUsersUseCase = LoginUseCase(repository = repository),
            checkCodeUseCase = CheckCodeUseCase(repository = repository),
            getBaseInfoUseCase = GetBaseInfoUseCase(repository = repository),
            setInfoUseCase = SetInfoUseCase(repository = repository),
            getInfoUseCase = GetInfoUseCase(repository = repository),
            getUserRoleUseCase = GetUserRoleUseCase(repository = repository),
            getCommonNotifications = GetCommonNotifications(repository = repository),
            getAllCommonNotifications = GetAllCommonNotifications(repository = repository),
            getPrivateNotifications = GetPrivateNotifications(repository = repository),
            getAllPrivateNotifications = GetAllPrivateNotifications(repository = repository),
            addNotification = AddNotification(repository = repository),
            getNotification = GetNotification(repository = repository),
            updateNotification = UpdateNotification(repository = repository),
            deleteNotification = DeleteNotification(repository = repository),
            getProfile = GetProfileUseCase(repository = repository),
            updateProfileUseCase = UpdateProfileUseCase(repository = repository),
            getAllUsersUseCase = GetAllUsersUseCase(repository = repository),
            deleteUserUseCase = DeleteUserUseCase(repository = repository),
            addUserUserCase = AddUserUserCase(repository = repository),
            getUserUseCase = GetUserUseCase(repository = repository),
            updateUserUseCase = UpdateUserUseCase(repository = repository),
            getAllJobsUseCase = GetAllJobsUseCase(repository = repository),
            getJobUseCase = GetJobUseCase(repository = repository),
            addJobUseCase = AddJobUseCase(repository = repository),
            addFileUseCase = AddFileUseCase(repository = repository),
            updateJobUseCase = UpdateJobUseCase(repository = repository),
            deleteFileUseCase = DeleteFileUseCase(repository = repository),
            deleteJobUseCase = DeleteJobUseCase(repository = repository),
            getAllSubmissionsUseCase = GetAllSubmissionsUseCase(repository = repository),
            addSubmissionUseCase = AddSubmissionUseCase(repository = repository),
            updateSubmissionUseCase = UpdateSubmissionUseCase(repository = repository),
            getUserSubmissionUseCase = GetUserSubmissionUseCase(repository = repository),
            getSubmissionUseCase = GetSubmissionUseCase(repository = repository),
            updateBaseInfo = UpdateBaseInfo(repository = repository),
            addBaseUseCase = AddBaseUseCase(repository = repository),
            updateBaseUseCase = UpdateBaseUseCase(repository = repository),
            deleteBaseUseCase = DeleteBaseUseCase(repository = repository),
            getBaseUseCase = GetBasesUseCase(repository = repository),
            getAllBasesUseCase = GetAllBasesUseCase(repository = repository),
            getAllActiveJobsUseCase = GetAllActiveJobsUseCase(repository = repository),
            getAllNotWorkedJobsUseCase = GetAllNotWorkedJobsUseCase(repository = repository),
            getAllUserJobsUseCase = GetAllUserJobsUseCase(repository = repository)
        )
    }
}