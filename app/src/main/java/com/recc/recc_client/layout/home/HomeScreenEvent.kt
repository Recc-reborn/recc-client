package com.recc.recc_client.layout.home

import com.recc.recc_client.layout.recyclerview.presenters.PlaylistPresenter

sealed class HomeScreenEvent {
    object PlaylistSelected: HomeScreenEvent()
    data class TracksFetched(val presenters: List<PlaylistPresenter>): HomeScreenEvent()
    object GetSpotifyToken: HomeScreenEvent()
    object CreateCustomPlaylistButtonPressed: HomeScreenEvent()
    object NoColdPlaylistAvailable: HomeScreenEvent()
}