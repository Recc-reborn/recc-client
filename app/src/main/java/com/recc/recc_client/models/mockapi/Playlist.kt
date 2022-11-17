package com.recc.recc_client.models.mockapi

import com.google.gson.annotations.SerializedName

data class Playlist(
    @SerializedName("id") val id: String = "",
    @SerializedName("createdAt") val createdAt: String = "",
    @SerializedName("name") val name: String = "",
    @SerializedName("image") val image: String = "",
    @SerializedName("tags") val tags: List<String>,
)