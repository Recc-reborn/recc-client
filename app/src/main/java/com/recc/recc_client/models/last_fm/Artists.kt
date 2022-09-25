package com.recc.recc_client.models.last_fm

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ArtistsList (
    @SerializedName("artist") val artist: List<Artist> = listOf(),
    @SerializedName("@attr") val attr: Attr = Attr()
): Serializable

data class Artists(
    @SerializedName("artists") val artists: ArtistsList = ArtistsList(),
): Serializable