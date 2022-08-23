package com.recc.recc_client.layout.welcome

import com.recc.recc_client.http.AuthHttp
import com.recc.recc_client.layout.common.EventViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WelcomeViewModel(private val http: AuthHttp): EventViewModel<WelcomeScreenEvent>() {
    fun getToken() {
        CoroutineScope(Dispatchers.IO).launch {
        }
    }
}