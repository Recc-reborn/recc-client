package com.recc.recc_client.layout.playlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.recc.recc_client.http.impl.MockApi
import com.recc.recc_client.layout.common.BaseEventViewModel
import com.recc.recc_client.layout.common.onFailure
import com.recc.recc_client.layout.common.onSuccess
import com.recc.recc_client.layout.recyclerview.presenters.SongPresenter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlaylistViewModel(private val mockApi: MockApi): BaseEventViewModel<PlaylistScreenEvent>() {

    private val _songs = MutableLiveData<List<SongPresenter>>()
    val songs: LiveData<List<SongPresenter>>
        get() = _songs

    fun getSongs() {
        viewModelScope.launch {
            CoroutineScope(Dispatchers.IO).launch {
                mockApi.getSongs()
                    .onSuccess { songList ->
                        _songs.postValue(songList.map { SongPresenter(it) })
                    }.onFailure {

                    }
            }
        }
    }
}