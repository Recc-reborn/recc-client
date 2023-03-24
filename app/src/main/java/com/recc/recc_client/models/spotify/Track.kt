package com.recc.recc_client.models.spotify

import com.google.gson.annotations.SerializedName

data class AlbumImage(
    @SerializedName("height") val height: Int = 0,
    @SerializedName("width") val width: Int = 0,
    @SerializedName("url") val url: String = ""
)

data class Album(
    @SerializedName("id") val id: String = "",
    @SerializedName("name") val name: String = "",
    @SerializedName("images") val images: List<AlbumImage> = listOf(),
    @SerializedName("uri") val uri: String = ""
)

data class Artist(
    @SerializedName("id") val id: String = "",
    @SerializedName("name") val name: String = "",
    @SerializedName("uri") val uri: String = ""
)

data class Item(
    @SerializedName("id") val id: String = "",
    @SerializedName("name") val name: String = "",
    @SerializedName("uri") val uri: String = "",
    @SerializedName("album") val album: Album = Album(),
    @SerializedName("artists") val artists: List<Artist> = listOf(),
    @SerializedName("duration_ms") val duration: Int = 0
)

data class Tracks(
    @SerializedName("items") val items: List<Item> = listOf(),
    @SerializedName("limit") val limit: Int = 1,
    @SerializedName("total") val total: Int = 0,
)

data class Track(
    @SerializedName("tracks") val tracks: Tracks = Tracks()
)
