package com.recc.recc_client.layout.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.recc.recc_client.http.impl.Control
import com.recc.recc_client.layout.common.BaseEventViewModel
import com.recc.recc_client.layout.common.onFailure
import com.recc.recc_client.layout.common.onSuccess
import com.recc.recc_client.layout.recyclerview.presenters.PlaylistPresenter
import com.recc.recc_client.models.control.Playlist
import com.recc.recc_client.models.control.Track
import com.recc.recc_client.utils.Alert
import com.recc.recc_client.utils.SharedPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val http: Control, private val sharedPreferences: SharedPreferences): BaseEventViewModel<HomeScreenEvent>() {

    private val _tracks = MutableLiveData<List<Track>>()
    val tracks: LiveData<List<Track>>
        get() = _tracks

    private val _selectedPlaylist = MutableLiveData<PlaylistPresenter>()
    val selectedPlaylist: LiveData<PlaylistPresenter>
        get() = _selectedPlaylist

    fun selectPlaylist(playlist: PlaylistPresenter) {
        _selectedPlaylist.postValue(playlist)
        postEvent(HomeScreenEvent.PlaylistSelected)
    }

    private fun getPlaylistTracks(fetchedPlaylists: List<Playlist>) {
        viewModelScope.launch {
            CoroutineScope(Dispatchers.IO).launch {
                val newPlaylists: MutableList<PlaylistPresenter> = mutableListOf()
                for (playlist in fetchedPlaylists) {
                    http.fetchPlaylistTracks(sharedPreferences.getToken().orEmpty(), playlist.id)
                        .onFailure { Alert("failed fetching tracks for playlist ${playlist.id}: ${playlist.title}") }
                        .onSuccess { tracks ->
                            Alert("tracks: $tracks")
                            newPlaylists.add(
                                PlaylistPresenter(Playlist(
                                    id = playlist.id,
                                    title = playlist.title,
                                    createdAt = playlist.createdAt,
                                    updatedAt = playlist.updatedAt,
                                    tracks = tracks
                                ))
                            )
                        }
                }
                postEvent(HomeScreenEvent.TracksFetched(newPlaylists))
            }
        }
    }

    fun getPlaylists() {
        viewModelScope.launch {
            CoroutineScope(Dispatchers.IO).launch {
                http.fetchPlaylists(sharedPreferences.getToken().orEmpty())
                    .onFailure {}
                    .onSuccess {
                        getPlaylistTracks(it)
                    }
            }
        }
    }
}