package com.recc.recc_client.http.def

import com.recc.recc_client.models.mockapi.Playlist
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface MockApiRouteDefinitions {
    @Headers("Accept: application/json")
    @GET("/playlists")
    suspend fun getPlaylists(): Response<List<Playlist>>
}