package com.recc.recc_client.layout.recyclerview.presenters

import com.recc.recc_client.models.control.Track

class TrackPresenter(track: Track): BasePresenter() {
    val id = track.id
    val album = track.album
    val albumArtUrl = track.albumArtUrl
    val artist = track.artist
    val duration = track.duration
    val title = track.title

    override fun areContentsTheSame(other: BasePresenter) = (other as TrackPresenter).id == id
}