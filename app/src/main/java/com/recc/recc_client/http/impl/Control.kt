package com.recc.recc_client.http.impl

import com.recc.recc_client.http.def.ServerRouteDefinitions
import com.recc.recc_client.layout.common.Result
import com.recc.recc_client.models.control.Artist
import com.recc.recc_client.models.control.Track

const val DEFAULT_CURRENT_PAGE = 1
const val DEFAULT_ARTISTS_PER_PAGE = 50

class Control(
    private val http: ServerRouteDefinitions
): BaseImpl() {


    suspend fun addPreferredArtists(token: String, artists: List<Int>): Result<String> {
        val query = http.addPreferredArtists(formatToken(token), artists)
        return handleNoBodyQuery(query)
    }

    suspend fun fetchArtists(
        page: Int = DEFAULT_CURRENT_PAGE,
        perPage: Int = DEFAULT_ARTISTS_PER_PAGE,
        search: String? = null): Result<List<Artist>> {
        val query = http.fetchTopArtists(perPage, page, search)
        return handleQuery(query) { it.data }
    }

    suspend fun addPreferredTracks(token: String, tracks: List<Int>): Result<String> {
        val query = http.addPreferredTracks(formatToken(token), tracks)
        return handleNoBodyQuery(query)
    }

    suspend fun fetchTracks(
        page: Int = DEFAULT_CURRENT_PAGE,
        perPage: Int = DEFAULT_ARTISTS_PER_PAGE,
        search: String? = null): Result<List<Track>> {
        val query = http.fetchTopTracks(perPage, page, search)
        return handleQuery(query) { it.data }
    }
}