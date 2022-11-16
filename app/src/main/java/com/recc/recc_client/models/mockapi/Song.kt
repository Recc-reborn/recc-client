package com.recc.recc_client.models.mockapi

import com.google.gson.annotations.SerializedName

class Song {
    @SerializedName("id") val id: String = ""
    @SerializedName("name") val name: String = ""
    @SerializedName("artist") val artist = ""
    @SerializedName("album") val album = ""
    @SerializedName("duration") val duration: Int = 0
    @SerializedName("albumImage") val albumImage: String = ""
    @SerializedName("tags") val tags: List<String> = listOf()
}