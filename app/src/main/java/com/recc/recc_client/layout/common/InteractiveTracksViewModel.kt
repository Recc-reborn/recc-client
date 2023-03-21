package com.recc.recc_client.layout.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.recc.recc_client.http.impl.Control
import com.recc.recc_client.http.impl.DEFAULT_CURRENT_PAGE
import com.recc.recc_client.layout.recyclerview.presenters.ArtistPresenter
import com.recc.recc_client.layout.recyclerview.presenters.BasePresenter
import com.recc.recc_client.layout.recyclerview.presenters.TrackPresenter
import kotlinx.coroutines.launch

abstract class InteractiveTracksViewModel<P: BasePresenter>(
    private val control: Control
): BaseEventViewModel<InteractiveItemsScreenEvent>(){
    private val _selectedItemColor = MutableLiveData<Int>()
    val selectedItemColor: LiveData<Int> = _selectedItemColor

    private val _unselectedItemColor = MutableLiveData<Int>()
    val unselectedItemColor: LiveData<Int> = _unselectedItemColor

    private val _selectedItems = MutableLiveData<Set<Int>>()
    val selectedItems: LiveData<Set<Int>> = _selectedItems

    protected val _presenterList = MutableLiveData<List<P>>()
    val presenterList: LiveData<List<P>> = _presenterList

    protected val _currentPage = MutableLiveData(DEFAULT_CURRENT_PAGE)

    fun addItem(id: Int) {
        viewModelScope.launch {
            var set = mutableSetOf<Int>()
            _selectedItems.value?.let { artistsSet ->
                set = artistsSet.toMutableSet()
            }
            set.add(id)
            _selectedItems.postValue(set)
        }
    }

    fun removeItem(id: Int) {
        viewModelScope.launch {
            var set = mutableSetOf<Int>()
            _selectedItems.value?.let { artistsSet ->
                set = artistsSet.toMutableSet()
            }
            set.remove(id)
            _selectedItems.postValue(set)
        }
    }

    fun setSelectedItemColor(color: Int) {
        _selectedItemColor.postValue(color)
    }

    fun setUnselectedItemColor(color: Int) {
        _unselectedItemColor.postValue(color)
    }

    fun replaceList(newList: List<P>) {
        viewModelScope.launch {
            _currentPage.postValue(DEFAULT_CURRENT_PAGE)
            _presenterList.postValue(newList)
        }
    }

    fun searchTracks(search: String) {
        viewModelScope.launch {
            control.fetchTracks(search = search)
                .onSuccess { tracks ->
                    replaceList(tracks.map { TrackPresenter(it) as P })
                }
                .onFailure {
                    postEvent(InteractiveItemsScreenEvent.SearchingItemsUnsuccessfully(it.message))
                }
        }
    }

    fun fetchNextTrackPage() {
        viewModelScope.launch {
            _currentPage.value?.let { oldPage ->
                val newPage = oldPage + 1
                control.fetchTracks(page = newPage)
                    .onSuccess { tracks ->
                        val presenters = tracks.map { TrackPresenter(it) }
                        _currentPage.postValue(newPage)
                        appendPageToList(presenters as List<P>)
                    }.onFailure {
                        postEvent(InteractiveItemsScreenEvent.FailedFetchingNextPage(it.message))
                    }
            }
        }
    }

    fun fetchNextArtistPage() {
        viewModelScope.launch {
            _currentPage.value?.let { oldPage ->
                val newPage = oldPage + 1
                control.fetchArtists(page = newPage)
                    .onSuccess { artists ->
                        val presenters = artists.map { ArtistPresenter(it) }
                        _currentPage.postValue(newPage)
                        appendPageToList(presenters as List<P>)
                    }.onFailure {
                        postEvent(InteractiveItemsScreenEvent.FailedFetchingNextPage(it.message))
                    }
            }
        }
    }

    private fun appendPageToList(newList: List<P>) {
        viewModelScope.launch {
            var oldList = mutableListOf<P>()
            _presenterList.value?.let { presenters ->
                oldList = presenters.toMutableList()
            }
            _presenterList.postValue(oldList + newList)
        }
    }

    fun fetchTracks() {
        viewModelScope.launch {
            control.fetchTracks()
                .onSuccess { tracks ->
                    postEvent(InteractiveItemsScreenEvent.ItemsFetched(tracks.map { TrackPresenter(it) }))
                }
                .onFailure {
                    postEvent(InteractiveItemsScreenEvent.ItemsNotFetched(it.message))
                }
        }
    }
}