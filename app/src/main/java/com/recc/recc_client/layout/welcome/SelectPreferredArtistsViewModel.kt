package com.recc.recc_client.layout.welcome

import androidx.lifecycle.viewModelScope
import com.recc.recc_client.http.impl.Control
import com.recc.recc_client.http.impl.DEFAULT_CURRENT_PAGE
import com.recc.recc_client.layout.common.InteractiveItemsScreenEvent
import com.recc.recc_client.layout.common.InteractiveTracksViewModel
import com.recc.recc_client.layout.common.onFailure
import com.recc.recc_client.layout.common.onSuccess
import com.recc.recc_client.layout.recyclerview.presenters.ArtistPresenter
import com.recc.recc_client.utils.Alert
import com.recc.recc_client.utils.SharedPreferences
import kotlinx.coroutines.launch

const val MIN_SELECTED_ARTISTS = 3

class SelectPreferredArtistsTracksViewModel(
    private val control: Control,
    private val sharedPreferences: SharedPreferences
): InteractiveTracksViewModel<ArtistPresenter>(control) {
    fun fetchArtists() {
        viewModelScope.launch {
            control.fetchArtists(DEFAULT_CURRENT_PAGE)
                .onSuccess { topArtists ->
                    val presenters = topArtists.map { ArtistPresenter(it) }
                    replaceList(presenters)
                    postEvent(InteractiveItemsScreenEvent.ArtistsFetched)
                }.onFailure {
                    postEvent(InteractiveItemsScreenEvent.ArtistsNotFetched(it.message))
                }
        }
    }

    fun searchArtist(artist: String) {
        viewModelScope.launch {
            control.fetchArtists(search = artist)
                .onSuccess { artists ->
                    val presenters = artists.map { ArtistPresenter(it) }
                    replaceList(presenters)
                }.onFailure {
                    // TODO: Error msg
                }
        }
    }

    fun preferredArtistsSelectedButtonPressed() {
        viewModelScope.launch {
            selectedItems.value?.let { artistsSet ->
                if (artistsSet.count() >= MIN_SELECTED_ARTISTS) {
                    val artistList = artistsSet.toList()
                    sharedPreferences.getToken()?.let { token ->
                        control.addPreferredArtists(token, artistList)
                            .onSuccess {
                                Alert("success: $it")
                            }.onFailure {
                                Alert("failure: $it")
                            }
                        postEvent(InteractiveItemsScreenEvent.GotoHomeBtnClicked)
                    }
                }
            }
        }
    }
}