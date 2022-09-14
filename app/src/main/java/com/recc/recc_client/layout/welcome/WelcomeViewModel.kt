package com.recc.recc_client.layout.welcome

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.recc.recc_client.http.def.DEFAULT_PAGE
import com.recc.recc_client.layout.common.BaseEventViewModel
import com.recc.recc_client.http.impl.LastFm
import com.recc.recc_client.layout.common.onFailure
import com.recc.recc_client.layout.common.onSuccess
import com.recc.recc_client.layout.recyclerview.presenters.ArtistPresenter
import com.recc.recc_client.utils.Alert
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WelcomeViewModel(private val http: LastFm): BaseEventViewModel<WelcomeScreenEvent>() {
    private val _selectedItemColor = MutableLiveData<Int>()
    val selectedItemColor: LiveData<Int>
        get() = _selectedItemColor

    private val _unselectedItemColor = MutableLiveData<Int>()
    val unselectedItemColor: LiveData<Int>
        get() = _unselectedItemColor

    private val _selectedArtists = MutableLiveData<Set<String>>()
    val selectedArtists: LiveData<Set<String>>
        get() = _selectedArtists

    private val _currentPage = MutableLiveData<Int>(DEFAULT_PAGE)
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

    fun appendPageToList(newList: List<ArtistPresenter>) {
        viewModelScope.launch {
            var oldList = mutableListOf<ArtistPresenter>()
            _presenterList.value?.let { artistPresenters ->
                oldList = artistPresenters.toMutableList()
            }
            oldList += newList
            _presenterList.postValue(oldList)
        }
    }

    fun addArtist(url: String) {
        viewModelScope.launch {
            Alert("adding: $url, ${_selectedArtists.value}")
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
                    http.getTopArtists(currentPage)
                        .onSuccess { artists ->
                            val presenterList = artists.artists.artist.map { ArtistPresenter(it) }
                            _currentPage.postValue(currentPage)
                            appendPageToList(presenterList)
                        }.onFailure {
                            Alert("Couldn't fetch page $currentPage of Top Artists")
                            // TODO: Error msg
                        }
                }
            }
        }
    }

    fun getTopArtists() {
        viewModelScope.launch {
            CoroutineScope(Dispatchers.IO).launch {
                http.getTopArtists()
                    .onSuccess { artists ->
                        val presenterList = artists.artists.artist.map { ArtistPresenter(it) }
                        postEvent(WelcomeScreenEvent.ArtistsFetched(presenterList))
                    }.onFailure {
                        postEvent(WelcomeScreenEvent.ArtistsNotFetched)
                    }
            }
        }
    }
}