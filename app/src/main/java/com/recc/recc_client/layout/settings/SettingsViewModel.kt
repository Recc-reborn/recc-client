package com.recc.recc_client.layout.settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.recc.recc_client.MainActivity
import com.recc.recc_client.http.impl.Auth
import com.recc.recc_client.layout.common.BaseEventViewModel
import com.recc.recc_client.utils.Alert
import com.recc.recc_client.utils.SharedPreferences
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val http: Auth,
    private val sharedPreferences: SharedPreferences
    ): BaseEventViewModel<SettingsScreenEvent>() {
    fun onBtnLogout(activity: MainActivity) {
        viewModelScope.launch {
            activity.logoutFromSpotify()
            sharedPreferences.removeSpotifyStatus()
            sharedPreferences.removeSpotifyToken()
            http.logout()
            sharedPreferences.removeToken()
            postEvent(SettingsScreenEvent.OnLoggedOut)
        }
    }

    fun setLogin() {
        postEvent(SettingsScreenEvent.SetLoginSpotifyBtn)
    }

    fun spotifyNotInstalled() {
        postEvent(SettingsScreenEvent.SpotifyNotInstalled)
    }

    fun handleSpotifyBtn(activity: MainActivity) {
        viewModelScope.launch {
            if (sharedPreferences.getSpotifyStatus()) {
                activity.logoutFromSpotify()
                postEvent(SettingsScreenEvent.SetLogoutSpotifyBtn)
            } else {
                val connected = MutableLiveData(false)
                activity.loginToSpotify(this@SettingsViewModel)
            }
        }
    }
}