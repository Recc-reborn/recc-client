package com.recc.recc_client.http.def

import com.recc.recc_client.models.spotify.*
import retrofit2.Response
import retrofit2.http.*

interface SpotifyApiRouteDefinitions {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @GET("v1/search?type=track")
    suspend fun getTrack(
        @Header("Authorization") token: String,
        @Query("q") q: String,
        @Query("market") market: String = "MX",
        @Query("limit") limit: Int = 1): Response<Track>

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @POST("v1/users/{user}/playlists")
    suspend fun createPlaylist (
        @Header("Authorization") token: String,
        @Path("user") user: String,
        @Body createPlaylist: CreatePlaylist): Response<Playlist>

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @POST("v1/playlists/{playlist_id}/tracks")
    suspend fun addTracksToPlaylist (
        @Header("Authorization") token: String,
        @Path("playlist_id") playlistId: String,
        @Body uris: AddTracksToPlaylist): Response<Snapshot>

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @GET("v1/me")
    suspend fun me (
        @Header("Authorization") token: String): Response<Me>
}