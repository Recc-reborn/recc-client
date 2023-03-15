package com.recc.recc_client.models.spotify

import com.google.gson.annotations.SerializedName

data class AddTracksToPlaylist(
    @SerializedName("uris") val uris: List<String> = listOf()
)

data class CreatePlaylist(
    @SerializedName("name") val name: String = "",
    @SerializedName("public") val public: Boolean = true,
    @SerializedName("collaborative") val collaborative: Boolean = false,
    @SerializedName("description") val description: String = ""
)

data class Playlist(
    @SerializedName("collaborative") val collaborative: Boolean = false,
    @SerializedName("description") val description: String = "",
    @SerializedName("external_url") val externalUrls: ExternalUrls = ExternalUrls(),
    @SerializedName("id") val id: String = "",
    @SerializedName("name") val name: String = "",
    @SerializedName("uri") val uri: String = ""
)
