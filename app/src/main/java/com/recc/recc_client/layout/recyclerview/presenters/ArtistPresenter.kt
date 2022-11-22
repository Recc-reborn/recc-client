package com.recc.recc_client.layout.recyclerview.presenters

import com.recc.recc_client.R
import com.recc.recc_client.layout.recyclerview.BasePresenter
import com.recc.recc_client.models.control.TopArtists

class ArtistPresenter(artist: TopArtists): BasePresenter() {
    val id: Int = artist.id
    val name: String = artist.name
    val listeners: Int = artist.listeners
    val lastFmUrl: String = artist.lastFmUrl
    val imageUrl: String = artist.imageUrl

    override val viewId: Int
        get() = R.layout.fragment_artist_grid_item

    override fun areContentsTheSame(other: BasePresenter): Boolean {
        if ((other as ArtistPresenter).id == id) {
            return true
        }
        return false
    }

}