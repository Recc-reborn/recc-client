package com.recc.recc_client.api.schema

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Track(
    @SerialName("id")
    var id: Int,
    @SerialName("created_at")
    var createdAt: String,
    @SerialName("updated_at")
    var updatedAt: String,
    @SerialName("title")
    var title: String,
    @SerialName("artist")
    var artist: String,
    @SerialName("duration")
    var duration: Int,
    @SerialName("genre")
    var genre: String
)