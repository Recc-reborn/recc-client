package com.recc.recc_client.layout.home

import com.recc.recc_client.layout.common.BaseScreenEvent

sealed class HomeScreenEvent: BaseScreenEvent() {
    object onLoggedOut: HomeScreenEvent()
}