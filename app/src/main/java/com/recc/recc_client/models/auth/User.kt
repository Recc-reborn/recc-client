package com.recc.recc_client.models.auth

import com.google.gson.annotations.SerializedName

const val ROLE_USER = "user"

data class User (
    @SerializedName("id") val id: Int = 0,
    @SerializedName("name") val name: String = "",
    @SerializedName("email") val email: String = "",
    @SerializedName("role") val role: String = "",
    @SerializedName("email_verified_at") val emailVerifiedAt: String = "",
    @SerializedName("created_at") val createdAt: String = "",
    @SerializedName("updated_at") val updatedAt: String = "",
)

data class CreateUser(
    @SerializedName("name") val name: String = "",
    @SerializedName("email") val email: String = "",
    @SerializedName("role") val role: String = "",
    @SerializedName("password") val password: String = "",
)