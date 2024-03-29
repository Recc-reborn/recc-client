package com.recc.recc_client.models.control

import com.google.gson.annotations.SerializedName

data class Playback (
    @SerializedName("track_id") val trackId: Int,
    @SerializedName("user_id") val userId: Int,
    @SerializedName("id") val id: Int
)