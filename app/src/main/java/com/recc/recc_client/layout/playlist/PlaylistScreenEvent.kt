package com.recc.recc_client.layout.playlist

import com.recc.recc_client.layout.recyclerview.presenters.SpotifyTrackPresenter

sealed class PlaylistScreenEvent {
    data class Error(val error: String): PlaylistScreenEvent()
    data class GoToSpotifyTrack(val presenter: SpotifyTrackPresenter): PlaylistScreenEvent()
    data class ErrorLoggingSpotify(val error: String): PlaylistScreenEvent()

    data class HandleExportButton(val state: Boolean): PlaylistScreenEvent()
}