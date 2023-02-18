package com.recc.recc_client.layout.settings

sealed class SettingsScreenEvent {
    object OnLoggedOut: SettingsScreenEvent()
    object SetLoginSpotifyBtn: SettingsScreenEvent()
    object SetLogoutSpotifyBtn: SettingsScreenEvent()
}
