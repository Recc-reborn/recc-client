package com.recc.recc_client.layout.welcome

import androidx.lifecycle.viewModelScope
import com.recc.recc_client.layout.common.BaseEventViewModel
import com.recc.recc_client.http.impl.LastFm
import com.recc.recc_client.layout.common.onFailure
import com.recc.recc_client.layout.common.onSuccess
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WelcomeViewModel(private val http: LastFm): BaseEventViewModel<WelcomeScreenEvent>() {
    fun getTopArtists() {
        viewModelScope.launch {
            CoroutineScope(Dispatchers.IO).launch {
                http.getTopArtists()
                    .onSuccess {
                        postEvent(WelcomeScreenEvent.ArtistsFetched(it))
                    }.onFailure {
                        postEvent(WelcomeScreenEvent.ArtistsNotFetched)
                    }
            }
        }
    }
}