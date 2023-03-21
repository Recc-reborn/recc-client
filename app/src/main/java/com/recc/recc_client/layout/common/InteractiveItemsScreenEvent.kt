package com.recc.recc_client.layout.common

import com.recc.recc_client.layout.recyclerview.presenters.TrackPresenter

sealed class InteractiveItemsScreenEvent {
    data class FailedFetchingNextPage(val error: String): InteractiveItemsScreenEvent()
    data class SearchingItemsUnsuccessfully(val error: String): InteractiveItemsScreenEvent()
    data class ItemsFetched(val trackPresenterList: List<TrackPresenter>): InteractiveItemsScreenEvent()
    data class ItemsNotFetched(val error: String): InteractiveItemsScreenEvent()
    object ItemsAdded: InteractiveItemsScreenEvent()
    data class FailedAddingItems(val error: String): InteractiveItemsScreenEvent()
    object GotoHomeBtnClicked: InteractiveItemsScreenEvent()
    object ArtistsFetched: InteractiveItemsScreenEvent()
    data class ArtistsNotFetched(val error: String): InteractiveItemsScreenEvent()
}