package com.recc.recc_client.http.impl

import com.recc.recc_client.http.def.SpotifyApiRouteDefinitions
import com.recc.recc_client.layout.common.Result
import com.recc.recc_client.layout.recyclerview.presenters.SpotifyTrackPresenter
import com.recc.recc_client.models.spotify.*

class Spotify(private val spotifyApi: SpotifyApiRouteDefinitions): BaseImpl() {

    suspend fun getTrack(
        token: String,
        q: String,
        limit: Int = 1,
        market: String = "MX"): Result<SpotifyTrackPresenter> {
        val query = spotifyApi.getTrack(formatToken(token), q, market, limit)
        return handleSpotifyQuery(query) { SpotifyTrackPresenter(it.tracks) }
    }

    suspend fun createPlaylist(
        token: String,
        user: String,
        name: String,
        description: String = "",
        public: Boolean = true,
        collaborative: Boolean = false): Result<Playlist> {
        val query = spotifyApi.createPlaylist(
            formatToken(token),
            user,
            CreatePlaylist(
                name = name,
                public = public,
                description = description,
                collaborative = collaborative
            )
        )
        return handleSpotifyQuery(query) { it }
    }

    suspend fun addTracksToPlaylist(
        token: String,
        playlistId: String,
        uris: List<String> = listOf()
    ): Result<Snapshot> {
        val query = spotifyApi.addTracksToPlaylist(formatToken(token), playlistId, AddTracksToPlaylist(uris = uris))
        return handleSpotifyQuery(query) { it }
    }

    suspend fun me(token: String): Result<Me> {
        val query = spotifyApi.me(formatToken(token))
        return handleSpotifyQuery(query) { it }
    }
}