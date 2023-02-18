package com.recc.recc_client.layout.welcome

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.recc.recc_client.http.impl.Control
import com.recc.recc_client.http.impl.DEFAULT_CURRENT_PAGE
import com.recc.recc_client.layout.common.InteractiveViewModel
import com.recc.recc_client.layout.common.onFailure
import com.recc.recc_client.layout.common.onSuccess
import com.recc.recc_client.layout.recyclerview.presenters.ArtistPresenter
import com.recc.recc_client.utils.Alert
import com.recc.recc_client.utils.SharedPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

const val MIN_SELECTED_ARTISTS = 3

class SelectPreferredArtistsViewModel(
    private val control: Control,
    private val sharedPreferences: SharedPreferences
): InteractiveViewModel<SelectPreferredArtistsScreenEvent>() {

    private val _presenterList = MutableLiveData<List<ArtistPresenter>>()
    val presenterList: LiveData<List<ArtistPresenter>> = _presenterList

        private fun appendPageToList(newList: List<ArtistPresenter>) {
        viewModelScope.launch {
            var oldList = mutableListOf<ArtistPresenter>()
            _presenterList.value?.let { artistPresenters ->
                oldList = artistPresenters.toMutableList()
            }
            _presenterList.postValue(oldList + newList)
        }
    }

    private fun replaceList(newList: List<ArtistPresenter>) {
        viewModelScope.launch {
            _currentPage.postValue(DEFAULT_CURRENT_PAGE)
            _presenterList.postValue(newList)
        }
    }

    fun fetchNextPage() {
        viewModelScope.launch {
            _currentPage.value?.let { oldPage ->
                CoroutineScope(Dispatchers.IO).launch {
                    val newPage = oldPage + 1
                    control.fetchArtists(page = newPage)
                        .onSuccess { topArtists ->
                            val presenters = topArtists.map { ArtistPresenter(it) }
                            _currentPage.postValue(newPage)
                            appendPageToList(presenters)
                        }.onFailure {
                            // TODO: Error msg
                        }
                }
            }
        }
    }

    fun fetchArtists() {
        viewModelScope.launch {
            CoroutineScope(Dispatchers.IO).launch {
                control.fetchArtists(DEFAULT_CURRENT_PAGE)
                    .onSuccess { topArtists ->
                        val presenters = topArtists.map { ArtistPresenter(it) }
                        replaceList(presenters)
                        postEvent(SelectPreferredArtistsScreenEvent.ArtistsFetched)
                    }.onFailure {
                        postEvent(SelectPreferredArtistsScreenEvent.ArtistsNotFetched)
                    }
            }
        }
    }

    fun searchArtist(artist: String) {
        viewModelScope.launch {
            CoroutineScope(Dispatchers.IO).launch {
                control.fetchArtists(search = artist)
                    .onSuccess { artists ->
                        val presenters = artists.map { ArtistPresenter(it) }
                        replaceList(presenters)
                    }.onFailure {
                        // TODO: Error msg
                    }
            }
        }
    }

    fun preferredArtistsSelectedButtonPressed() {
        viewModelScope.launch {
            CoroutineScope(Dispatchers.IO).launch {
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
                            postEvent(SelectPreferredArtistsScreenEvent.GotoHomeBtnClicked)
                        }
                    }
                }
            }
        }
    }
}