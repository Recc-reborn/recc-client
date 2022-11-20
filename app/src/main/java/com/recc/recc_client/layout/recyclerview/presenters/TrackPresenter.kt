package com.recc.recc_client.layout.recyclerview.presenters

import com.recc.recc_client.R
import com.recc.recc_client.models.control.Track

class TrackPresenter(track: Track): BasePresenter() {
    val id = track.id
    val albumArtUrl = track.albumArtUrl
    val artist = track.artist
    val url = track.url
    val duration = track.duration
    val title = track.title

    override val viewId: Int
        get() = R.layout.fragment_track_item

    override fun areContentsTheSame(other: BasePresenter): Boolean {
        if ((other as TrackPresenter).id == id) {
            return true
        }
        return false
    }

}