package com.recc.recc_client.layout.home

import com.recc.recc_client.http.impl.Auth
import com.recc.recc_client.layout.common.BaseEventViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val http: Auth): BaseEventViewModel<HomeScreenEvent>() {
    fun onBtnLogout() {
        CoroutineScope(Dispatchers.IO).launch {
            http.logout()
            postEvent(HomeScreenEvent.onLoggedOut)
        }
    }
}