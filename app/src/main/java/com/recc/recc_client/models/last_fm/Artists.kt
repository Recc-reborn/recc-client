package com.recc.recc_client.models.last_fm

import com.google.gson.annotations.SerializedName

data class ArtistsList (
    @SerializedName("artist") val artist: List<Artist> = listOf(),
    @SerializedName("@attr") val attr: Attr = Attr()
)

data class Artists(
    @SerializedName("artists") val artists: ArtistsList = ArtistsList(),
)