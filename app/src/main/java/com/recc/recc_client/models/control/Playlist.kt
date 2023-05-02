package com.recc.recc_client.models.control

import com.google.gson.annotations.SerializedName

data class Playlist(
    @SerializedName("id") val id: Int = 0,
    @SerializedName("title") val title: String = "",
    @SerializedName("created_at") val createdAt: String = "",
    @SerializedName("updated_at") val updatedAt: String = "",
    val tracks: List<Track> = listOf()
)

data class CustomPlaylist(
    @SerializedName("title") val title: String = "",
    @SerializedName("track_ids") val tracks: List<Int> = listOf()
)