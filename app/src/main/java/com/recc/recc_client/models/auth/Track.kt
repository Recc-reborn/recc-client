package com.recc.recc_client.models.auth

import com.google.gson.annotations.SerializedName

data class Track (
    @SerializedName("id") val id: Int,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String,
    @SerializedName("title") val title: String,
    @SerializedName("artist") val artist: String,
    @SerializedName("duration") val duration: Int,
    @SerializedName("genre") val genre: String,
)
