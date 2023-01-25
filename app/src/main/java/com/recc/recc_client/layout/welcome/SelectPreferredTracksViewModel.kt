package com.recc.recc_client.layout.welcome

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.recc.recc_client.http.impl.Control
import com.recc.recc_client.http.impl.DEFAULT_CURRENT_PAGE
import com.recc.recc_client.layout.common.InteractiveViewModel
import com.recc.recc_client.layout.common.onFailure
import com.recc.recc_client.layout.common.onSuccess
import com.recc.recc_client.layout.recyclerview.presenters.TrackPresenter
import com.recc.recc_client.utils.Alert
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

const val MIN_SELECTED_TRACKS = 3

class SelectPreferredTracksViewModel(private val control: Control): InteractiveViewModel<SelectPreferredTracksScreenEvent>() {

    private val _presenterList = MutableLiveData<List<TrackPresenter>>()
    val presenterList: LiveData<List<TrackPresenter>> = _presenterList

    fun replaceList(newList: List<TrackPresenter>) {
        viewModelScope.launch {
            _currentPage.postValue(DEFAULT_CURRENT_PAGE)
            _presenterList.postValue(newList)
        }
    }

    fun searchTracks(search: String) {
        viewModelScope.launch {
            CoroutineScope(Dispatchers.IO).launch {
                control.fetchTracks(search = search)
                    .onSuccess { tracks ->
                        replaceList(tracks.map { TrackPresenter(it) })
                    }
                    .onFailure {
                        postEvent(SelectPreferredTracksScreenEvent.SearchingTracksUnsuccessfully(it.message))
                    }
            }
        }
    }

    fun fetchNextPage() {
        viewModelScope.launch {
            _currentPage.value?.let { oldPage ->
                CoroutineScope(Dispatchers.IO).launch {
                    val newPage = oldPage + 1
                    control.fetchTracks(page = newPage)
                        .onSuccess { tracks ->
                            val presenters = tracks.map { TrackPresenter(it) }
                            _currentPage.postValue(newPage)
                            appendPageToList(presenters)
                        }.onFailure {
                            postEvent(SelectPreferredTracksScreenEvent.FailedFetchingNextPage(it.message))
                        }
                }
            }
        }
    }

    private fun appendPageToList(newList: List<TrackPresenter>) {
        viewModelScope.launch {
            var oldList = mutableListOf<TrackPresenter>()
            _presenterList.value?.let { artistPresenters ->
                oldList = artistPresenters.toMutableList()
            }
            _presenterList.postValue(oldList + newList)
        }
    }

    fun fetchTracks() {
        viewModelScope.launch {
            CoroutineScope(Dispatchers.IO).launch {
                control.fetchTracks()
                    .onSuccess {tracks ->
                        postEvent(SelectPreferredTracksScreenEvent.TracksFetched(tracks.map { TrackPresenter(it) }))
                    }
                    .onFailure {
                        postEvent(SelectPreferredTracksScreenEvent.TracksNotFetched(it.message))
                    }
            }
        }
    }

    fun preferredTracksSelectedButtonPressed() {
        viewModelScope.launch {
            CoroutineScope(Dispatchers.IO).launch {
                if (token.value.isNullOrBlank() || selectedItems.value.isNullOrEmpty()) {
                    Alert("no entra")
                    return@launch
                }
                control.addPreferredTracks(token.value!!, selectedItems.value!!.toMutableList())
                    .onSuccess {
                        postEvent(SelectPreferredTracksScreenEvent.PreferredTracksAdded)
                    }
                    .onFailure {
                        postEvent(SelectPreferredTracksScreenEvent.FailedAddingPreferredTracks(it.message))
                    }
            }
        }
    }
}