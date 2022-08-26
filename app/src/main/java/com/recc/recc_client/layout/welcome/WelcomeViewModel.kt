package com.recc.recc_client.layout.welcome

import com.recc.recc_client.layout.common.BaseEventViewModel
import com.recc.recc_client.http.impl.LastFm
import com.recc.recc_client.layout.common.onFailure
import com.recc.recc_client.layout.common.onSuccess
import com.recc.recc_client.utils.Alert
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WelcomeViewModel(private val http: LastFm): BaseEventViewModel<WelcomeScreenEvent>() {
    fun getToken() {
        CoroutineScope(Dispatchers.IO).launch {
            http.getTopArtists()
                .onSuccess {
                    Alert("Artists: $it")
                }.onFailure {
                    Alert("Error: $it")
                }
        }
    }
}