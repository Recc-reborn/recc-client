package com.recc.recc_client.layout.playlist

import android.widget.PopupMenu
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.recc.recc_client.R
import com.recc.recc_client.http.impl.Control
import com.recc.recc_client.http.impl.Spotify
import com.recc.recc_client.layout.common.BaseEventViewModel
import com.recc.recc_client.layout.common.onFailure
import com.recc.recc_client.layout.common.onSuccess
import com.recc.recc_client.layout.recyclerview.presenters.TrackPresenter
import com.recc.recc_client.utils.Alert
import com.recc.recc_client.utils.SharedPreferences
import kotlinx.coroutines.launch

class PlaylistViewModel(
    private val http: Control,
    private val spotifyApi: Spotify,
    private val sharedPreferences: SharedPreferences): BaseEventViewModel<PlaylistScreenEvent>() {
    private val _tracks = MutableLiveData<List<TrackPresenter>>()
    val tracks: LiveData<List<TrackPresenter>>
        get() = _tracks

    private val _title = MutableLiveData<String>()
    fun setPlaylistTitle(title: String) {
        _title.postValue(title)
    }

    fun getTracks(playlistId: Int) {
        viewModelScope.launch {
            http.fetchPlaylistTracks(sharedPreferences.getToken(), playlistId)
            .onSuccess { songList ->
                _tracks.postValue(songList.map { TrackPresenter(it) })
            }.onFailure {
                postEvent(PlaylistScreenEvent.Error(it.message))
            }
        }
    }

    private fun formattedSpotifyQuery(presenter: TrackPresenter): String {
        var query = ""
        if (presenter.artist.isNotEmpty()) {
            query += presenter.artist + " "
        }
        if (presenter.title.isNotEmpty()) {
            query += presenter.title + " "
        }
        if (presenter.album.isNotEmpty()) {
            query += presenter.album
        }
        return query
    }

    private fun getSpotifyTrackUri(presenter: TrackPresenter) {
        viewModelScope.launch {
            val token = sharedPreferences.getSpotifyToken()
            val query = formattedSpotifyQuery(presenter)
            spotifyApi.getTrack(token, query)
                .onSuccess {
                    postEvent(PlaylistScreenEvent.GoToSpotifyTrack(it))
                }.onFailure {
                    postEvent(PlaylistScreenEvent.ErrorLoggingSpotify(it.message))
                }
        }
    }

    fun handleMenuPressed(menu: PopupMenu, presenter: TrackPresenter) {
        viewModelScope.launch {
            menu.inflate(R.menu.track_popup_menu)
            menu.setOnMenuItemClickListener { menuItem ->
                when(menuItem.itemId) {
                    R.id.action_open_with_spotify -> {
                        getSpotifyTrackUri(presenter)
                        true
                    }
                    else -> { false }
                }
            }
            menu.show()
        }
    }

    private fun addTracksToSpotifyPlaylist(uris: List<String>, playlistId: String) {
        viewModelScope.launch {
            spotifyApi.addTracksToPlaylist(sharedPreferences.getSpotifyToken(), playlistId, uris)
                .onFailure { postEvent(PlaylistScreenEvent.Error("Error adding tracks to playlist")) }
        }
    }

    fun exportSpotifyPlaylist() {
        postEvent(PlaylistScreenEvent.HandleExportButton(false))
        viewModelScope.launch {
            var errorLoggingIn = false
            _tracks.value?.let { tracks ->
                val uris: MutableList<String> = mutableListOf()
                for (track in tracks) {
                    if (errorLoggingIn) {
                        break
                    }
                    spotifyApi.getTrack(
                        sharedPreferences.getSpotifyToken(),
                        formattedSpotifyQuery(track)
                    ).onSuccess {
                        uris.add(it.uri)
                    }.onFailure {
                        errorLoggingIn = true
                    }
                }
                if (uris.isNotEmpty()) {
                    spotifyApi.createPlaylist(
                        sharedPreferences.getSpotifyToken(),
                        sharedPreferences.getSpotifyUserId(),
                        _title.value.orEmpty(),
                        "" // TODO: make description
                    )
                        .onFailure {
                            postEvent(PlaylistScreenEvent.Error("Error creating playlist"))
                        }.onSuccess { playlist ->
                            addTracksToSpotifyPlaylist(uris, playlist.id)
                        }
                }
            }
            if (errorLoggingIn) {
                postEvent(PlaylistScreenEvent.ErrorLoggingSpotify("Not authenticated"))
            } else {
                postEvent(PlaylistScreenEvent.HandleExportButton(true))
            }
        }
    }
}