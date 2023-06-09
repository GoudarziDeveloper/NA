package com.tinyDeveloper.na.utils

object Constants {
    //const val BASE_URL = "http://192.168.201.144/"
    //const val BASE_URL = "https://erena.ir/"
    const val BASE_URL = "https://yademansystem.ir/"
    const val SERVER_EXT = "erena"
    const val UPLOAD_URL = "/uploads/"
    private const val SERVER_VERSION = "v3"
    const val BASE_ID = "100000"
    const val SMS_CODE_LENGTH = 6
    var APP_CHAT_KEY = "fuv7p5h58tq5"

    var MANAGER_NAME = "مدیر"
    var PART_NAME = "بخش"
    var CENTER_NAME = "شرکت"

    const val SCREENS_DURATION = 500
    const val ON_BOARDING_PAGE_COUNT = 5
    const val PERSONAL_CHAT_KEY = "messaging:erena_event"

    const val GET_COMMON_NOTIFICATIONS = "${SERVER_EXT}/api/${SERVER_VERSION}/notifications/public/getAllActives.php"
    const val GET_ALL_COMMON_NOTIFICATIONS = "${SERVER_EXT}/api/${SERVER_VERSION}/notifications/public/getAll.php"
    const val GET_PERSONAL_NOTIFICATIONS = "${SERVER_EXT}/api/${SERVER_VERSION}/notifications/private/getAllActives.php"
    const val GET_ALL_PERSONAL_NOTIFICATIONS = "${SERVER_EXT}/api/${SERVER_VERSION}/notifications/private/getAll.php"

    const val GET_ALL_JOBS = "${SERVER_EXT}/api/${SERVER_VERSION}/jobs/getAll.php"
    const val GET_ALL_USER_JOBS = "${SERVER_EXT}/api/${SERVER_VERSION}/jobs/sort/getAll.php"
    const val GET_ALL_WORKED_JOBS = "${SERVER_EXT}/api/${SERVER_VERSION}/jobs/sort/worked.php"
    const val GET_ALL_NOT_WORKED_JOBS = "${SERVER_EXT}/api/${SERVER_VERSION}/jobs/sort/notWorked.php"

    const val LOGIN = "${SERVER_EXT}/api/${SERVER_VERSION}/users/login.php"
    const val CHECK_CODE = "${SERVER_EXT}/api/${SERVER_VERSION}/users/check.php"
    const val GET_BASE_INFO = "${SERVER_EXT}/api/${SERVER_VERSION}/baseInfo/get.php"
    const val GET_USER_ROLE = "${SERVER_EXT}/api/${SERVER_VERSION}/users/profile/userRole.php"
    const val ADD_NOTIFICATION = "${SERVER_EXT}/api/${SERVER_VERSION}/notifications/add.php"
    const val GET_NOTIFICATION = "${SERVER_EXT}/api/${SERVER_VERSION}/notifications/get.php"
    const val UPDATE_NOTIFICATION = "${SERVER_EXT}/api/${SERVER_VERSION}/notifications/update.php"
    const val DELETE_NOTIFICATION = "${SERVER_EXT}/api/${SERVER_VERSION}/notifications/delete.php"
    const val GET_PROFILE = "${SERVER_EXT}/api/${SERVER_VERSION}/users/profile/get.php"
    const val UPDATE_PROFILE = "${SERVER_EXT}/api/${SERVER_VERSION}/users/profile/update.php"
    const val GET_ALL_USERS = "${SERVER_EXT}/api/${SERVER_VERSION}/users/getAll.php"
    const val DELETE_USER = "${SERVER_EXT}/api/${SERVER_VERSION}/users/delete.php"
    const val ADD_USER = "${SERVER_EXT}/api/${SERVER_VERSION}/users/add.php"
    const val GET_USER = "${SERVER_EXT}/api/${SERVER_VERSION}/users/get.php"
    const val UPDATE_USER = "${SERVER_EXT}/api/${SERVER_VERSION}/users/update.php"
    const val GET_JOB = "${SERVER_EXT}/api/${SERVER_VERSION}/jobs/get.php"
    const val ADD_JOB = "${SERVER_EXT}/api/${SERVER_VERSION}/jobs/add.php"
    const val ADD_FILE = "${SERVER_EXT}/api/${SERVER_VERSION}/files/add.php"
    const val UPDATE_JOB = "${SERVER_EXT}/api/${SERVER_VERSION}/jobs/update.php"
    const val DELETE_FILE = "${SERVER_EXT}/api/${SERVER_VERSION}/files/delete.php"
    const val DELETE_JOB = "${SERVER_EXT}/api/${SERVER_VERSION}/jobs/delete.php"
    const val GET_ALL_SUBMISSIONS = "${SERVER_EXT}/api/${SERVER_VERSION}/submissions/getAll.php"
    const val ADD_SUBMISSION = "${SERVER_EXT}/api/${SERVER_VERSION}/submissions/add.php"
    const val UPDATE_SUBMISSION = "${SERVER_EXT}/api/${SERVER_VERSION}/submissions/update.php"
    const val GET_SUBMISSION = "${SERVER_EXT}/api/${SERVER_VERSION}/submissions/get.php"
    const val GET_USER_SUBMISSION = "${SERVER_EXT}/api/${SERVER_VERSION}/submissions/getUserSubmission.php"
    const val UPDATE_BASE_INFO = "${SERVER_EXT}/api/${SERVER_VERSION}/baseInfo/update.php"
    const val GET_ALL_BASES = "${SERVER_EXT}/api/${SERVER_VERSION}/bases/getAll.php"
    const val GET_BASE = "${SERVER_EXT}/api/${SERVER_VERSION}/bases/get.php"
    const val DELETE_BASE = "${SERVER_EXT}/api/${SERVER_VERSION}/bases/delete.php"
    const val ADD_BASE = "${SERVER_EXT}/api/${SERVER_VERSION}/bases/add.php"
    const val UPDATE_BASE = "${SERVER_EXT}/api/${SERVER_VERSION}/bases/update.php"

    const val SPLASH_SCREEN = "splash/"
    const val PHONE_SCREEN = "phone/"
    const val MAIN_SCREEN = "main/"
    const val WELCOME_SCREEN = "welcome/"
    const val PASSWORD_SCREEN = "password/"
    const val VERIFICATION_SCREEN = "verification/"
    const val HALF_ALPHA = 0.5f
    const val SPLASH_SCREEN_DELAY = 0L
    const val VERIFICATION_TIME = 120
    const val APP_INFO_PREFERENCES = "app_data_store"
    const val APP_INFO_PHONE_ARGUMENT = "phone_data_store_key"
    const val APP_INFO_PASSWORD_ARGUMENT = "password_data_store_key"
    const val USER_SCREEN_ARGUMENT = "user_phone"
    const val JOB_SCREEN_ARGUMENT = "job_id"
    const val SUBMISSION_SCREEN_ARGUMENT = "job_id"
    const val SUBMISSION_SCREEN_ARGUMENT2 = "job_status"
    const val SUBMISSIONS_SCREEN_ARGUMENT = "job_id"
    const val BASE_SCREEN_ARGUMENT = "base_id"

    const val NOTIFICATIONS_SCREEN = "NotificationsScreen/"
    const val NOTIFICATION_DETAILS_TITLE = "اعلان"
    const val NOTIFICATION_DETAILS_SCREEN = "NotificationDetailScreen/"
    const val NOTIFICATIONS_SCREEN_TITLE = "اعلانات"
    const val JOBS_SCREEN = "JobsScreen/"
    const val JOBS_SCREEN_TITLE = "کارخواسته ها"
    const val JOB_DETAIL_SCREEN = "JobDetailScreen/"
    const val JOB_DETAIL_TITLE = "جزئیات کارخواسته"
    const val SUBMISSIONS_SCREEN = "SubmissionsScreen/"
    const val SUBMISSIONS_SCREEN_TITLE = "تحویل شده ها"
    const val SUBMISSION_SCREEN = "SubmissionScreen/"
    const val SUBMISSION_SCREEN_TITLE = "تحویل شده"
    const val USERS_SCREEN = "UsersScreen/"
    const val USERS_SCREEN_TITLE = "کاربران"
    const val PROFILE_SCREEN = "ProfileScreen/"
    const val PROFILE_SCREEN_TITLE = "وضعیت"
    const val NOTIFICATION_SCREEN = "NotificationScreen"
    const val NOTIFICATION_TITLE = "اعلان جدید"
    const val JOB_SCREEN = "JobScreen"
    const val JOB_TITLE = "کارخواسته"
    const val USER_SCREEN = "UserScreen/"
    const val USER_TITLE = "کاربر"
    const val BASES_SCREEN = "BasesScreen/"
    //const val BASES_SCREEN_TITLE = "پایگاه ها"
    var BASES_SCREEN_TITLE = "$PART_NAME ها"
    const val BASE_SCREEN = "BaseScreen/"
    //const val BASE_SCREEN_TITLE = "پایگاه"
    var BASE_SCREEN_TITLE = PART_NAME
    const val SETTINGS_SCREEN = "SettingsScreen"
    const val SETTINGS_SCREEN_TITLE = "تنظیمات"
    const val WELCOME_ARGUMENT = "isMain"
    const val PERSONAL_CHAT_SCREEN = "personal_chat_screen/"
    const val PERSONAL_CHAT_SCREEN_TITLE = "گفتگو شخصی"
}