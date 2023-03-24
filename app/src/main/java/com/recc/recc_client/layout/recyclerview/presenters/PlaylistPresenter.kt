package com.recc.recc_client.layout.recyclerview.presenters

import com.recc.recc_client.R
import com.recc.recc_client.models.control.Playlist

class PlaylistPresenter(playlist: Playlist): BasePresenter {
    val id = playlist.id
    val title = playlist.title
    val tracks: List<TrackPresenter> = playlist.tracks.map { TrackPresenter(it) }


    override fun areContentsTheSame(other: BasePresenter) = (other as PlaylistPresenter).id == id
}