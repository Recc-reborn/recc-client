package com.recc.recc_client.layout.playlist

import com.recc.recc_client.layout.recyclerview.presenters.SpotifyTrackPresenter

sealed class PlaylistScreenEvent {
    data class ErrorFetchingTracks(val error: String): PlaylistScreenEvent()
    data class GoToSpotifyTrack(val presenter: SpotifyTrackPresenter): PlaylistScreenEvent()
    data class ErrorSearchingTrack(val error: String): PlaylistScreenEvent()
}