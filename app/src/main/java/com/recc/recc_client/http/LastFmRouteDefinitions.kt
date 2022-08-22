package com.recc.recc_client.http

import com.recc.recc_client.models.last_fm.Token
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

const val LAST_FM_GET_TOKEN_METHOD = "auth.gettoken"

interface LastFmRouteDefinitions {
    @Headers("Accept: application/json")
    @GET("/?format=json")
    suspend fun baseRequest(@Query("method") method: String, @Query("api_key") apiKey: String): Response<Token>
}