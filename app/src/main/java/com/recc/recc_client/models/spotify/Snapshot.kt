package com.recc.recc_client.models.spotify

import com.google.gson.annotations.SerializedName

data class Snapshot(
    @SerializedName("snapshot_id") val id: String = ""
)
