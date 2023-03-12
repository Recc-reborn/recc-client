package com.recc.recc_client.layout.recyclerview.presenters

import com.recc.recc_client.models.control.Track

class TrackPresenter(track: Track): BasePresenter() {
    val id = track.id
    val album = track.album.orEmpty()
    val albumArtUrl = track.albumArtUrl.orEmpty()
    val artist = track.artist.orEmpty()
    val duration = track.duration
    val title = track.title.orEmpty()

    override fun areContentsTheSame(other: BasePresenter) = (other as TrackPresenter).id == id
}