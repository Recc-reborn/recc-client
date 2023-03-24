package com.recc.recc_client.layout.recyclerview.presenters

import com.recc.recc_client.models.spotify.Tracks

class SpotifyTrackPresenter(tracks: Tracks): BasePresenter {
    val id = tracks.items.first().id
    val album = tracks.items.first().album.name
    val albumArtUrl = tracks.items.first().album.images.first().url
    val artist = tracks.items.first().artists.first().name
    val duration = tracks.items.first().duration
    val title = tracks.items.first().name
    val artistUri = tracks.items.first().artists.first().uri
    val albumUri = tracks.items.first().album.uri
    val uri = tracks.items.first().uri

    override fun areContentsTheSame(other: BasePresenter) = (other as SpotifyTrackPresenter).id == id
}