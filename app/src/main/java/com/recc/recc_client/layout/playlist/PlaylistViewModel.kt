package com.recc.recc_client.layout.playlist

import android.content.Intent
import android.widget.PopupMenu
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.recc.recc_client.R
import com.recc.recc_client.http.impl.MockApi
import com.recc.recc_client.http.impl.Spotify
import com.recc.recc_client.layout.common.BaseEventViewModel
import com.recc.recc_client.layout.common.onFailure
import com.recc.recc_client.layout.common.onSuccess
import com.recc.recc_client.layout.recyclerview.presenters.TrackPresenter
import com.recc.recc_client.utils.Alert
import com.recc.recc_client.utils.SharedPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlaylistViewModel(
    private val mockApi: MockApi,
    private val spotifyApi: Spotify,
    private val sharedPreferences: SharedPreferences): BaseEventViewModel<PlaylistScreenEvent>() {

    private val _tracks = MutableLiveData<List<TrackPresenter>>()
    val tracks: LiveData<List<TrackPresenter>>
        get() = _tracks

    fun getTracks() {
        viewModelScope.launch {
            CoroutineScope(Dispatchers.IO).launch {
                mockApi.getTracks()
                    .onSuccess { songList ->
                        _tracks.postValue(songList.map { TrackPresenter(it) })
                    }.onFailure {
                        postEvent(PlaylistScreenEvent.ErrorFetchingTracks(it.message))
                    }
            }
        }
    }

    private fun getSpotifyTrackUri(presenter: TrackPresenter) {
        viewModelScope.launch {
            val token = sharedPreferences.getSpotifyToken()
            Alert("token: $token")
            val query = "${presenter.artist} ${presenter.title} ${presenter.album}"
            spotifyApi.getTrack(token, query)
                .onSuccess {
                    postEvent(PlaylistScreenEvent.GoToSpotifyTrack(it))
                } .onFailure {
                    postEvent(PlaylistScreenEvent.ErrorSearchingTrack(it.message))
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