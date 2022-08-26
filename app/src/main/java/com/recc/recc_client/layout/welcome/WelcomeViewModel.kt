package com.recc.recc_client.layout.welcome

import com.recc.recc_client.http.impl.Auth
import com.recc.recc_client.layout.common.BaseEventViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WelcomeViewModel(private val http: Auth): BaseEventViewModel<WelcomeScreenEvent>() {
    fun getToken() {
        CoroutineScope(Dispatchers.IO).launch {
        }
    }
}