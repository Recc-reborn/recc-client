package com.recc.recc_client.layout.welcome

sealed class WelcomeScreenEvent {
    object ArtistsFetched: WelcomeScreenEvent()
    object ArtistsNotFetched: WelcomeScreenEvent()
    object GotoHomeBtnClicked: WelcomeScreenEvent()
}