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
import com.recc.recc_client.utils.SharedPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlaylistViewModel(
    private val http: Control,
    private val spotifyApi: Spotify,
    private val sharedPreferences: SharedPreferences): BaseEventViewModel<PlaylistScreenEvent>() {
    private val _tracks = MutableLiveData<List<TrackPresenter>>()
    val tracks: LiveData<List<TrackPresenter>>
        get() = _tracks

    fun getTracks(playlistId: Int) {
        viewModelScope.launch {
            CoroutineScope(Dispatchers.IO).launch {
                    http.fetchPlaylistTracks(sharedPreferences.getToken().orEmpty(), playlistId) .onSuccess { songList ->
                        _tracks.postValue(songList.map { TrackPresenter(it) })
                    }.onFailure {
                        postEvent(PlaylistScreenEvent.ErrorFetchingTracks(it.message))
                    }
            }
        }
    }

    private fun getSpotifyTrackUri(presenter: TrackPresenter) {
        viewModelScope.launch {
            CoroutineScope(Dispatchers.IO).launch {
                val token = sharedPreferences.getSpotifyToken()
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
                spotifyApi.getTrack(token, query)
                    .onSuccess {
                        postEvent(PlaylistScreenEvent.GoToSpotifyTrack(it))
                    }.onFailure {
                        postEvent(PlaylistScreenEvent.ErrorSearchingTrack(it.message))
                    }
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
}