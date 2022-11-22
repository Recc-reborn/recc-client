package com.recc.recc_client.layout.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.recc.recc_client.http.impl.MockApi
import com.recc.recc_client.layout.common.BaseEventViewModel
import com.recc.recc_client.layout.common.onFailure
import com.recc.recc_client.layout.common.onSuccess
import com.recc.recc_client.layout.recyclerview.presenters.PlaylistPresenter
import com.recc.recc_client.layout.recyclerview.presenters.TrackPresenter
import com.recc.recc_client.models.control.Playlist
import com.recc.recc_client.models.control.Track
import com.recc.recc_client.utils.Alert
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val mockApi: MockApi): BaseEventViewModel<HomeScreenEvent>() {

    private val _playlists = MutableLiveData<List<PlaylistPresenter>>()
    val playlists: LiveData<List<PlaylistPresenter>>
        get() = _playlists

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

    fun getTracks() {
        viewModelScope.launch {
            CoroutineScope(Dispatchers.IO).launch {
                mockApi.getTracks()
                    .onFailure {}
                    .onSuccess { tracks ->
                        // TODO: Change by recc data
                        playlists.value?.let { playlists ->
                            val newPlaylist = playlists.map { PlaylistPresenter(Playlist(it.id, it.title, tracks)) }
                            postEvent(HomeScreenEvent.TracksFetched(newPlaylist))
                        }
                    }
            }
        }
    }

    fun getPlaylists() {
        viewModelScope.launch {
            CoroutineScope(Dispatchers.IO).launch {
                mockApi.getPlaylists()
                    .onFailure {}
                    .onSuccess { res ->
                        _playlists.postValue(res.map { PlaylistPresenter(it) })
                        postEvent(HomeScreenEvent.PlaylistFetched)
                    }
            }
        }
    }
}