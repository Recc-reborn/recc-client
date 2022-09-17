package com.recc.recc_client.layout.welcome

import com.recc.recc_client.layout.recyclerview.presenters.ArtistPresenter

sealed class WelcomeScreenEvent {
    object ArtistsFetched: WelcomeScreenEvent()
    object ArtistsNotFetched: WelcomeScreenEvent()
    data class ArtistSearchFetched(val presenters: List<ArtistPresenter>): WelcomeScreenEvent()
}