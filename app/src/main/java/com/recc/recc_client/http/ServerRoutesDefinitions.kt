package com.recc.recc_client.http

import com.recc.recc_client.models.responses.Playback
import com.recc.recc_client.models.responses.Track
import com.recc.recc_client.models.responses.User
import retrofit2.Response
import retrofit2.http.*

interface ServerRoutesDefinitions {
    @GET("/users")
    suspend fun getUsers(): Response<User>

    @GET("/users/{user}")
    suspend fun getUser(@Path("user") user: Int): Response<User>

    @GET("/users/me")
    suspend fun getUserMe(): Response<User>

    @POST("/users/")
    suspend fun postUser(): Response<User>

    @POST("/token")
    suspend fun postToken(): Response<String>

    @DELETE("/token")
    suspend fun deleteToken(): Response<String>

    @GET("/tracks")
    suspend fun getTracks(): Response<Track>

    @PUT("/tracks")
    suspend fun putTracks(): Response<String>

    @POST("/playbacks")
    suspend fun postPlaybacks(): Response<Playback>

    @DELETE("/tracks/{track}")
    suspend fun deleteTrack(@Path("track") track: Int): Response<String>

    // TODO: PUT and DELETE petitions (and still missing POST petitions)
}