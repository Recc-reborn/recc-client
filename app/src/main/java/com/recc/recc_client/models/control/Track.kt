package com.recc.recc_client.models.control

import com.google.gson.annotations.SerializedName

data class Track(
    @SerializedName("id") val id: Int = 0,
    @SerializedName("title") val title: String = "",
    @SerializedName("artist") val artist: String = "",
    @SerializedName("url") val url: String = "",
    @SerializedName("album_art_url") val albumArtUrl: String = "",
    @SerializedName("duration") val duration: Int = 0
)