package com.recc.recc_client.models.last_fm

import com.google.gson.annotations.SerializedName

data class Artist(
    @SerializedName("name") val name: String = "",
    @SerializedName("playcount") val playcount: String = "",
    @SerializedName("listeners") val listeners: String = "",
    @SerializedName("mbid") val mbid: String = "",
    @SerializedName("url") val url: String = "",
    @SerializedName("image") val image: List<Image> = listOf()
)
