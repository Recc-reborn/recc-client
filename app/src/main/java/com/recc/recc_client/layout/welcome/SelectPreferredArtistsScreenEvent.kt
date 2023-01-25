package com.recc.recc_client.layout.welcome

sealed class SelectPreferredArtistsScreenEvent {
    object ArtistsFetched: SelectPreferredArtistsScreenEvent()
    object ArtistsNotFetched: SelectPreferredArtistsScreenEvent()
    object GotoHomeBtnClicked: SelectPreferredArtistsScreenEvent()
}