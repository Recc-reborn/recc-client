package com.recc.recc_client.layout.home

import com.recc.recc_client.http.AuthHttp
import com.recc.recc_client.layout.common.EventViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val httpApi: AuthHttp): EventViewModel<HomeScreenEvent>() {
    fun onBtnLogout() {
        CoroutineScope(Dispatchers.IO).launch {
            httpApi.logout()
            postEvent(HomeScreenEvent.onLoggedOut)
        }
    }
}