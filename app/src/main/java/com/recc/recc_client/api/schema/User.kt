package com.recc.recc_client.api.schema


data class User(
    val name: String,
    val email: String,
    val password: String
)

data class UserLogin(
    val email: String,
    val password: String,
    val deviceName: String
)

data class UserInfo(
    val id: Int,
    val name: String,
    val email: String,
    val role: String,
    val emailVerifiedAt: String?,
    val createdAt: String,
    val updatedAt: String
)