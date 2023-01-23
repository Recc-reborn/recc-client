package com.recc.recc_client.models.auth

import com.google.gson.annotations.SerializedName

data class User (
    @SerializedName("id") val id: Int = 0,
    @SerializedName("name") val name: String = "",
    @SerializedName("email") val email: String = "",
    @SerializedName("role") val role: String = "",
    @SerializedName("email_verified_at") val emailVerifiedAt: String = "",
    @SerializedName("created_at") val createdAt: String = "",
    @SerializedName("updated_at") val updatedAt: String = "",
    @SerializedName("has_set_preferred_artists") val hasSetPreferredArtists: Boolean = false,
    @SerializedName("has_set_preferred_tracks") val hasSetPreferredTracks: Boolean = false
)

data class CreateUser(
    @SerializedName("name") val name: String = "",
    @SerializedName("email") val email: String = "",
    @SerializedName("password") val password: String = "",
)