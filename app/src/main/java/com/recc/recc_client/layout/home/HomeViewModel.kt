package com.recc.recc_client.layout.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.recc.recc_client.http.impl.MockApi
import com.recc.recc_client.layout.common.BaseEventViewModel
import com.recc.recc_client.layout.common.onFailure
import com.recc.recc_client.layout.common.onSuccess
import com.recc.recc_client.layout.recyclerview.presenters.PlaylistPresenter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val mockApi: MockApi): BaseEventViewModel<HomeScreenEvent>() {

    private val _playlists = MutableLiveData<List<PlaylistPresenter>>()
    val playlists: LiveData<List<PlaylistPresenter>>
        get() = _playlists

    fun getPlaylists() {
        viewModelScope.launch {
            CoroutineScope(Dispatchers.IO).launch {
                mockApi.getPlaylists()
                    .onFailure {

                    }.onSuccess { res ->
                        _playlists.postValue(res.map { PlaylistPresenter(it) })
                    }
            }
        }
    }
}