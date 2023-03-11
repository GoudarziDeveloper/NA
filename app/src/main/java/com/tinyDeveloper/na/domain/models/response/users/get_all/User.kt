package com.tinyDeveloper.na.domain.models.response.users.get_all

data class User(
    val baseId: String,
    val birthDay: String,
    val date: String,
    val fatherName: String,
    val firstName: String,
    val id: String,
    val image: String,
    val lastName: String,
    val nationalCode: String,
    val password: String,
    val phone: String,
    val baseName: String,
    val score: Int,
    val status: String
)