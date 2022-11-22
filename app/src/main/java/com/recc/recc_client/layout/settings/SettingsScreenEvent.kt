package com.recc.recc_client.layout.settings

import com.recc.recc_client.layout.home.PagerScreenEvent

sealed class SettingsScreenEvent {
    object onLoggedOut: SettingsScreenEvent()
}
