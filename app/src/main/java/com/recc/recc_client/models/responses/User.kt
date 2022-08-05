package com.recc.recc_client.models.responses

import com.google.gson.annotations.SerializedName

data class User (
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("role") val role: String,
    @SerializedName("email_verified_at") val emailVerifiedAt: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String,
)