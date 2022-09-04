package com.recc.recc_client.layout.welcome

import com.recc.recc_client.models.last_fm.Artists

sealed class WelcomeScreenEvent {
    data class ArtistsFetched(val artist: Artists): WelcomeScreenEvent()
    object ArtistsNotFetched: WelcomeScreenEvent()
}