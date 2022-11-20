package com.recc.recc_client.models.control

import com.google.gson.annotations.SerializedName

data class Playlist(
    @SerializedName("id") val id: String = "",
    @SerializedName("title") val title: String = "",
    @SerializedName("tracks") val tracks: List<Track> = listOf()
)