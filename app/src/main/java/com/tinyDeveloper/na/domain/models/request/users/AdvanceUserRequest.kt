package com.tinyDeveloper.na.domain.models.request.users

data class AdvanceUserRequest(
    val addBase: Int,
    val addFile: Int,
    val addJob: Int,
    val addNotification: Int,
    val addSubmission: Int,
    val addUser: Int,
    val adminPassword: String,
    val adminPhone: String,
    val baseId: String,
    val birthDay: String,
    val deleteBase: Int,
    val deleteFile: Int,
    val deleteJob: Int,
    val deleteNotification: Int,
    val deleteSubmission: Int,
    val deleteUser: Int,
    val editBaseInfo: Int,
    val fatherName: String,
    val firstName: String,
    val getBaseInfo: Int,
    val getBases: Int,
    val getFile: Int,
    val getJob: Int,
    val getNotifications: Int,
    val getSubmissions: Int,
    val getUser: Int,
    val getUsers: Int,
    val image: String,
    val lastName: String,
    val nationalCode: String,
    val password: String,
    val phone: String,
    val status: String,
    val updateBase: Int,
    val updateJob: Int,
    val updateNotification: Int,
    val updateSubmission: Int,
    val updateUser: Int,
    val updateUsers: Int
)