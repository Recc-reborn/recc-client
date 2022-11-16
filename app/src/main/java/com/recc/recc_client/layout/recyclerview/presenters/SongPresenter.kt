package com.recc.recc_client.layout.recyclerview.presenters

import com.recc.recc_client.R
import com.recc.recc_client.models.mockapi.Song

class SongPresenter(song: Song): BasePresenter() {
    val id = song.id
    val name = song.name
    val album = song.album
    val albumCover = song.albumImage
    val tags = song.tags
    val duration = song.duration
    val artist = song.artist

    override val viewId: Int
        get() = R.layout.fragment_song_item

    override fun areContentsTheSame(other: BasePresenter): Boolean {
        if ((other as SongPresenter).id == id) {
            return true
        }
        return false
    }
}