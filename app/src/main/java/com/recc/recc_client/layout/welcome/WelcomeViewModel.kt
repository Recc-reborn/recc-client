package com.recc.recc_client.layout.welcome

import com.recc.recc_client.http.LastFmHttp
import com.recc.recc_client.layout.common.EventViewModel
import com.recc.recc_client.layout.common.onFailure
import com.recc.recc_client.layout.common.onSuccess
import com.recc.recc_client.utils.Alert
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WelcomeViewModel(private val http: LastFmHttp): EventViewModel<WelcomeScreenEvent>() {
    fun getToken() {
        CoroutineScope(Dispatchers.IO).launch {
            http.getToken()
                .onSuccess {
                    Alert("token: $it")
                }
                .onFailure {
                    Alert("$it")
                }
        }
    }
}