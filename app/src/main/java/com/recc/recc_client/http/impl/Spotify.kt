package com.recc.recc_client.http.impl

import com.recc.recc_client.http.def.SpotifyApiRouteDefinitions
import com.recc.recc_client.layout.common.Result
import com.recc.recc_client.layout.recyclerview.presenters.SpotifyTrackPresenter

class Spotify(private val spotifyApi: SpotifyApiRouteDefinitions): BaseImpl() {

    suspend fun getTrack(token: String,
                         q: String,
                         limit: Int = 1,
                         market: String = "MX"): Result<SpotifyTrackPresenter> {
        val query = spotifyApi.getTrack(formatToken(token), q, market, limit)
        return handleSpotifyQuery(query) { SpotifyTrackPresenter(it.tracks) }
    }
}