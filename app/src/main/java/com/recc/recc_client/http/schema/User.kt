package com.recc.recc_client.http.schema


data class User(
    val name: String,
    val email: String,
    val password: String
)

data class UserLogin(
    val email: String,
    val password: String,
    val device_name: String
)

data class UserInfo(
    val id: Int,
    val name: String,
    val email: String,
    val role: String,
    val email_verified_at: String?,
    val created_at: String,
    val updated_at: String
)