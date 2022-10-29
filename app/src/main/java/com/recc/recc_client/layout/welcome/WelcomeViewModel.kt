package com.recc.recc_client.layout.welcome

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.recc.recc_client.http.def.DEFAULT_CHART_PAGE
import com.recc.recc_client.http.impl.Control
import com.recc.recc_client.layout.common.BaseEventViewModel
import com.recc.recc_client.http.impl.LastFm
import com.recc.recc_client.layout.common.onFailure
import com.recc.recc_client.layout.common.onSuccess
import com.recc.recc_client.layout.recyclerview.presenters.ArtistPresenter
import com.recc.recc_client.utils.Alert
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

const val MIN_SELECTED_ARTISTS = 3

class WelcomeViewModel(
    private val http: LastFm,
    private val control: Control
): BaseEventViewModel<WelcomeScreenEvent>() {
    private val _selectedItemColor = MutableLiveData<Int>()
    val selectedItemColor: LiveData<Int>
        get() = _selectedItemColor

    private val _unselectedItemColor = MutableLiveData<Int>()
    val unselectedItemColor: LiveData<Int>
        get() = _unselectedItemColor

    private val _selectedArtists = MutableLiveData<Set<String>>()
    val selectedArtists: LiveData<Set<String>>
        get() = _selectedArtists

    private val _currentPage = MutableLiveData(DEFAULT_CHART_PAGE)
    val currentPage: LiveData<Int>
        get() = _currentPage

    private val _presenterList = MutableLiveData<List<ArtistPresenter>>()
    val presenterList: LiveData<List<ArtistPresenter>>
        get() = _presenterList


    fun setSelectedItemColor(color: Int) {
        _selectedItemColor.postValue(color)
    }

    fun setUnselectedItemColor(color: Int) {
        _unselectedItemColor.postValue(color)
    }

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
            _currentPage.postValue(DEFAULT_CHART_PAGE)
            _presenterList.postValue(newList)
        }
    }

    fun addArtist(url: String) {
        viewModelScope.launch {
            var set = mutableSetOf<String>()
            _selectedArtists.value?.let { artistsSet ->
                set = artistsSet.toMutableSet()
            }
            if (url.isNotEmpty()) {
                set.add(url)
                _selectedArtists.postValue(set)
            }
        }
    }

    fun removeArtist(url: String) {
        viewModelScope.launch {
            var set = mutableSetOf<String>()
            _selectedArtists.value?.let { artistsSet ->
                set = artistsSet.toMutableSet()
            }
            set.remove(url)
            _selectedArtists.postValue(set)
        }
    }

    fun getNextPage() {
        viewModelScope.launch {
            _currentPage.value?.let { oldPage ->
                CoroutineScope(Dispatchers.IO).launch {
                    val currentPage = oldPage + 1
                    Alert("currentPage: $currentPage")
                    http.getTopArtists(currentPage)
                        .onSuccess { artists ->
                            val list = artists.artists.artist.map { ArtistPresenter(it) }
                            _currentPage.postValue(currentPage)
                            appendPageToList(list)
                        }.onFailure {
                            // TODO: Error msg
                        }
                }
            }
        }
    }

    fun getTopArtists() {
        viewModelScope.launch {
            CoroutineScope(Dispatchers.IO).launch {
                http.getTopArtists(DEFAULT_CHART_PAGE)
                    .onSuccess { artists ->
                        val list = artists.artists.artist.map { ArtistPresenter(it) }
                        replaceList(list)
                        postEvent(WelcomeScreenEvent.ArtistsFetched)
                    }.onFailure {
                        postEvent(WelcomeScreenEvent.ArtistsNotFetched)
                    }
            }
        }
    }

    fun searchArtist(artist: String) {
        viewModelScope.launch {
            CoroutineScope(Dispatchers.IO).launch {
                http.getArtistSearch(artist)
                    .onSuccess { search ->
                        val list = search.results.artistMatches.artist.map { ArtistPresenter(it) }
                        replaceList(list)
                    }.onFailure {
                        // TODO: Error msg
                    }
            }
        }
    }

    fun gotoHomeBtnClicked() {
        viewModelScope.launch {
            CoroutineScope(Dispatchers.IO).launch {
                _selectedArtists.value?.let { artistsSet ->
                    if (artistsSet.count() >= MIN_SELECTED_ARTISTS) {
                        val artistList = artistsSet.toList()
                        token.value?.let { token ->
                            control.addPreferredArtists(token, artistList)
                                .onSuccess {
                                    Alert("success: $it")
                                }.onFailure {
                                    Alert("failure: $it")
                                }
                            postEvent(WelcomeScreenEvent.GotoHomeBtnClicked)
                        }
                    }
                }
            }
        }
    }
}