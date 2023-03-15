package com.recc.recc_client.models.spotify

import com.google.gson.annotations.SerializedName

data class ExternalUrls(
    @SerializedName("spotify") val url: String = ""
)

data class Me(
    @SerializedName("display_name") val displayName: String = "",
    @SerializedName("external_urls") val url: ExternalUrls = ExternalUrls(),
    @SerializedName("id") val id: String = "",
    @SerializedName("uri") val uri: String = ""
)
