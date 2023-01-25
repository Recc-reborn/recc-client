package com.recc.recc_client.layout.welcome

import com.recc.recc_client.layout.recyclerview.presenters.TrackPresenter

sealed class SelectPreferredTracksScreenEvent {
    data class TracksFetched(val trackPresenterList: List<TrackPresenter>): SelectPreferredTracksScreenEvent()
    data class TracksNotFetched(val error: String): SelectPreferredTracksScreenEvent()
    data class SearchingTracksUnsuccessfully(val error: String): SelectPreferredTracksScreenEvent()
    object PreferredTracksAdded: SelectPreferredTracksScreenEvent()
    data class FailedAddingPreferredTracks(val error: String): SelectPreferredTracksScreenEvent()
}
