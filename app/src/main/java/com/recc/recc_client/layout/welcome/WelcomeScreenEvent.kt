package com.recc.recc_client.layout.welcome

import com.recc.recc_client.layout.recyclerview.presenters.ArtistPresenter

sealed class WelcomeScreenEvent {
    data class ArtistsFetched(val presenters: List<ArtistPresenter>): WelcomeScreenEvent()
    object ArtistsNotFetched: WelcomeScreenEvent()
}