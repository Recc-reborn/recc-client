package com.recc.recc_client.layout.recyclerview.presenters

import com.recc.recc_client.models.control.Artist

class ArtistPresenter(artist: Artist): BasePresenter {
    val id: Int = artist.id
    val name: String = artist.name
    val listeners: Int = artist.listeners
    val lastFmUrl: String = artist.lastFmUrl
    val imageUrl: String = artist.imageUrl

    override fun areContentsTheSame(other: BasePresenter) = (other as ArtistPresenter).id == id

}