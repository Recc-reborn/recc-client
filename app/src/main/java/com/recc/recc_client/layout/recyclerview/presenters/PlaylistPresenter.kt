package com.recc.recc_client.layout.recyclerview.presenters

import com.recc.recc_client.R
import com.recc.recc_client.models.mockapi.Playlist

class PlaylistPresenter(playlist: Playlist): BasePresenter() {
    val id = playlist.id
    val createdAt = playlist.createdAt
    val name = playlist.name
    val image = playlist.image
    val tags = playlist.tags
    val artists = playlist.artists

    override val viewId: Int
        get() = R.layout.fragment_playlist_item

    override fun areContentsTheSame(other: BasePresenter): Boolean {
        if ((other as PlaylistPresenter).id == id) {
            return true
        }
        return false
    }
}