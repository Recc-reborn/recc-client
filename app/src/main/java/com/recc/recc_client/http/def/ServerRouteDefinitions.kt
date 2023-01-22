package com.recc.recc_client.http.def

import com.recc.recc_client.http.impl.DEFAULT_ARTISTS_PER_PAGE
import com.recc.recc_client.http.impl.DEFAULT_CURRENT_PAGE
import com.recc.recc_client.models.auth.*
import com.recc.recc_client.models.control.BaseRequest
import com.recc.recc_client.models.control.TopArtists
import retrofit2.Response
import retrofit2.http.*

interface ServerRouteDefinitions {
    @Headers("Accept: application/json")
    @GET("api/users")
    suspend fun getUsers(): Response<User>

    @Headers("Accept: application/json")
    @GET("api/users/{user}")
    suspend fun getUser(@Path("user") user: Int): Response<User>

    @Headers("Accept: application/json")
    @GET("api/users/me")
    suspend fun getUserMe(@Header("Authorization") token: String): Response<User>

    @Headers("Accept: application/json")
    @POST("api/users/")
    suspend fun postUser(@Body user: CreateUser): Response<User>

    @Headers("Accept: application/json")
    @POST("api/auth/token")
    suspend fun postToken(@Body createToken: CreateToken): Response<Token>

    @Headers("Accept: application/json")
    @DELETE("api/auth/token")
    suspend fun deleteToken(): Response<SimpleResponse>

    @Headers("Accept: application/json")
    @GET("api/tracks")
    suspend fun getTracks(): Response<Track>

    @Headers("Accept: application/json")
    @PUT("api/tracks")
    suspend fun putTracks(): Response<String>

    @Headers("Accept: application/json")
    @POST("api/playbacks")
    suspend fun postPlaybacks(): Response<Playback>

    @Headers("Accept: application/json")
    @DELETE("api/tracks/{track}")
    suspend fun deleteTrack(@Path("track") track: Int): Response<String>

    @Headers("Accept: application/json")
    @PATCH("api/user/preferred-artists")
    suspend fun addPreferredArtists(@Header("Authorization") token: String, @Body preferredArtists: List<Int>): Response<Void>

    @Headers("Accept: application/json")
    @GET("api/artists")
    suspend fun getTopArtists(
        @Query("per_page") perPage: Int = DEFAULT_ARTISTS_PER_PAGE,
        @Query("page") page: Int = DEFAULT_CURRENT_PAGE,
        @Query("search") search: String? = null): Response<BaseRequest<TopArtists>>

    // TODO: PUT and DELETE queries (and still missing POST petitions)
}