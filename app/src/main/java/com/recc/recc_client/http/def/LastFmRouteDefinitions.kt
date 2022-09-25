package com.recc.recc_client.http.def

import com.recc.recc_client.models.last_fm.Artists
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

const val DEFAULT_LIMIT = 50
const val DEFAULT_PAGE = 1
const val GET_TOP_ARTIST_METHOD = "chart.gettopartists"

interface LastFmRouteDefinitions {
    @Headers("Accept: application/json")
    @GET("/2.0/")
    suspend fun getTopArtists (
        @Query("method") method: String = GET_TOP_ARTIST_METHOD,
        @Query("api_key") apiKey: String,
        @Query("limit") limit: Int = DEFAULT_LIMIT,
        @Query("page") page: Int = DEFAULT_PAGE,
        @Query("format") format: String = "json"): Response<Artists>

    @Headers("Accept: application/json")
    @GET("/2.0/")
    suspend fun getArtistsByTag (
            @Query("method") method: String,
            @Query("api_key") apiKey: String,
            @Query("limit") limit: Int = DEFAULT_LIMIT,
            @Query("page") page: Int = DEFAULT_PAGE,
            @Query("format") format: String = "json")
}