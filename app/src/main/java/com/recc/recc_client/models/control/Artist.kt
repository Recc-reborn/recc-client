package com.recc.recc_client.models.control

import com.google.gson.annotations.SerializedName

data class Artist(
    @SerializedName("id") val id: Int = 0,
    @SerializedName("name") val name: String = "",
    @SerializedName("mbid") val mbid: String = "",
    @SerializedName("listeners") val listeners: Int = 0,
    @SerializedName("image_url") val imageUrl: String = "",
    @SerializedName("last_fm_url") val lastFmUrl: String = ""
)