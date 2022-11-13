package com.recc.recc_client.layout.recyclerview.presenters

import com.recc.recc_client.R
import com.recc.recc_client.models.last_fm.Artist
import com.recc.recc_client.models.last_fm.Image

class ArtistPresenter(artist: Artist): BasePresenter() {
    val name: String = artist.name
    val playcount: String = artist.playcount
    val listeners: String = artist.listeners
    val mbid: String = artist.mbid
    val url: String = artist.url
    val streamable: String = artist.streamable
    val image: List<Image> = artist.image

    override val viewId: Int
        get() = R.layout.fragment_artist_grid_item

    override fun areContentsTheSame(other: BasePresenter): Boolean {
        if ((other as ArtistPresenter).url == url) {
            return true
        }
        return false
    }

}