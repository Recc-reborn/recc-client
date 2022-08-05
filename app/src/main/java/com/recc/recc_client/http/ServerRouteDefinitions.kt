package com.recc.recc_client.http

import com.recc.recc_client.models.responses.Token
import com.recc.recc_client.models.responses.Playback
import com.recc.recc_client.models.responses.Track
import com.recc.recc_client.models.responses.User
import retrofit2.Response
import retrofit2.http.*

interface ServerRouteDefinitions {
    @GET("api/users")
    suspend fun getUsers(): Response<User>

    @GET("api/users/{user}")
    suspend fun getUser(@Path("user") user: Int): Response<User>

    @GET("api/users/me")
    suspend fun getUserMe(): Response<User>

    @POST("api/users/")
    suspend fun postUser(): Response<User>

    @POST("api/auth/token")
    suspend fun postToken(@Body token: Token): Response<String?>

    @DELETE("api/auth/token")
    suspend fun deleteToken(): Response<String>

    @GET("api/tracks")
    suspend fun getTracks(): Response<Track>

    @PUT("api/tracks")
    suspend fun putTracks(): Response<String>

    @POST("api/playbacks")
    suspend fun postPlaybacks(): Response<Playback>

    @DELETE("api/tracks/{track}")
    suspend fun deleteTrack(@Path("track") track: Int): Response<String>

    // TODO: PUT and DELETE queries (and still missing POST petitions)
}