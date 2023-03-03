package com.recc.recc_client.http.def

import com.recc.recc_client.models.spotify.Track
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

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
}