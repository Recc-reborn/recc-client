package com.recc.recc_client.models.last_fm

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ArtistMatches(
    @SerializedName("artist") val artist: List<Artist> = listOf()
): Serializable

data class Results(
    @SerializedName("opensearch:totalResults") val totalResults: String = "0",
    @SerializedName("opensearch:startIndex") val startIndex: String = "0",
    @SerializedName("opensearch:itemsPerPage") val itemsPerPage: String = "0",
    @SerializedName("artistmatches") val artistMatches: ArtistMatches = ArtistMatches()
): Serializable

data class Search(
    @SerializedName("results") val results: Results = Results()
): Serializable
