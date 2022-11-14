package com.recc.recc_client.layout.settings

import com.recc.recc_client.http.impl.Auth
import com.recc.recc_client.layout.common.BaseEventViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingsViewModel(private val http: Auth): BaseEventViewModel<SettingsScreenEvent>() {
    fun onBtnLogout() {
        CoroutineScope(Dispatchers.IO).launch {
            http.logout()
            postEvent(SettingsScreenEvent.onLoggedOut)
        }
    }
}