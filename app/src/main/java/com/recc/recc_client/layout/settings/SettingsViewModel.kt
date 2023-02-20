package com.recc.recc_client.layout.settings

import com.recc.recc_client.MainActivity
import com.recc.recc_client.http.impl.Auth
import com.recc.recc_client.layout.common.BaseEventViewModel
import com.recc.recc_client.utils.SharedPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val http: Auth,
    private val sharedPreferences: SharedPreferences
    ): BaseEventViewModel<SettingsScreenEvent>() {
    fun onBtnLogout() {
        CoroutineScope(Dispatchers.IO).launch {
            http.logout()
            postEvent(SettingsScreenEvent.OnLoggedOut)
        }
    }

    fun handleSpotifyBtn(activity: MainActivity) {
        if (sharedPreferences.getSpotifyStatus()) {
            postEvent(SettingsScreenEvent.SetLogoutSpotifyBtn)
            activity.logoutFromSpotify()
        } else {
            postEvent(SettingsScreenEvent.SetLoginSpotifyBtn)
            activity.loginToSpotify()
        }
    }
}