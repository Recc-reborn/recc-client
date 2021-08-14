package com.recc.recc_client.api.schema

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("name")
    val name: String,
    @SerialName("email")
    val email: String,
    @SerialName("password")
    val password: String
)

@Serializable
data class UserLogin(
    @SerialName("email")
    val email: String,
    @SerialName("password")
    val password: String,
    @SerialName("device_name")
    val deviceName: String
)

@Serializable
data class UserInfo(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("email")
    val email: String,
    @SerialName("role")
    val role: String,
    @SerialName("email_verified_at")
    val emailVerifiedAt: String?,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("updated_at")
    val updatedAt: String
)