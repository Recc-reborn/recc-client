package com.recc.recc_client.layout.welcome

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.recc.recc_client.http.impl.Control
import com.recc.recc_client.http.impl.DEFAULT_CURRENT_PAGE
import com.recc.recc_client.layout.common.BaseEventViewModel
import com.recc.recc_client.layout.common.onFailure
import com.recc.recc_client.layout.common.onSuccess
import com.recc.recc_client.layout.recyclerview.presenters.ArtistPresenter
import com.recc.recc_client.utils.Alert
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

const val MIN_SELECTED_ARTISTS = 3

class WelcomeViewModel(
    private val control: Control
): BaseEventViewModel<WelcomeScreenEvent>() {
    private val _selectedItemColor = MutableLiveData<Int>()
    val selectedItemColor: LiveData<Int>
        get() = _selectedItemColor

    private val _unselectedItemColor = MutableLiveData<Int>()
    val unselectedItemColor: LiveData<Int>
        get() = _unselectedItemColor

    private val _selectedArtists = MutableLiveData<Set<Int>>()
    val selectedArtists: LiveData<Set<Int>>
        get() = _selectedArtists

    private val _currentPage = MutableLiveData(DEFAULT_CURRENT_PAGE)
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
            _currentPage.postValue(DEFAULT_CURRENT_PAGE)
            _presenterList.postValue(newList)
        }
    }

    fun addArtist(id: Int) {
        viewModelScope.launch {
            var set = mutableSetOf<Int>()
            _selectedArtists.value?.let { artistsSet ->
                set = artistsSet.toMutableSet()
            }
            set.add(id)
            _selectedArtists.postValue(set)
        }
    }

    fun removeArtist(id: Int) {
        viewModelScope.launch {
            var set = mutableSetOf<Int>()
            _selectedArtists.value?.let { artistsSet ->
                set = artistsSet.toMutableSet()
            }
            set.remove(id)
            _selectedArtists.postValue(set)
        }
    }

    fun getNextPage() {
        viewModelScope.launch {
            _currentPage.value?.let { oldPage ->
                CoroutineScope(Dispatchers.IO).launch {
                    val currentPage = oldPage + 1
                    control.getArtists(currentPage)
                        .onSuccess { topArtists ->
                            val presenters = topArtists.map { ArtistPresenter(it) }
                            _currentPage.postValue(currentPage)
                            appendPageToList(presenters)
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
                control.getArtists(DEFAULT_CURRENT_PAGE)
                    .onSuccess { topArtists ->
                        val presenters = topArtists.map { ArtistPresenter(it) }
                        replaceList(presenters)
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
                control.getArtists(search = artist)
                    .onSuccess { artists ->
                        val presenters = artists.map { ArtistPresenter(it) }
                        replaceList(presenters)
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