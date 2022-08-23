package com.recc.recc_client.http

import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Headers

const val DEFAULT_LIMIT = 50
const val DEFAULT_PAGE = 1

interface LastFmRouteDefinitions {
    @Headers("Accept: application/json")
    @GET("/")
    fun baseRequest(@Field("method") method: String,
                    @Field("api_key") apiKey: String,
                    @Field("format") format: String = "json",
                    @Field("limit") limit: Int = DEFAULT_LIMIT,
                    @Field("page") page: Int = DEFAULT_PAGE
    )
}