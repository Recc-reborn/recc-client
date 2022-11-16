package com.recc.recc_client.layout.recyclerview.view_holders

import com.bumptech.glide.Glide
import com.recc.recc_client.databinding.FragmentSongItemBinding
import com.recc.recc_client.layout.playlist.PlaylistViewModel
import com.recc.recc_client.layout.recyclerview.presenters.SongPresenter
import com.recc.recc_client.utils.secondsToMinutes

class SongViewHolder(
    private val binding: FragmentSongItemBinding,
    private val viewModel: PlaylistViewModel
): BaseViewHolder(binding.root) {

    fun bind(presenter: SongPresenter) {
        Glide.with(binding.ivImage)
            .load(presenter.albumCover)
            .fitCenter()
            .into(binding.ivImage)
        binding.tvName.text = presenter.name
        binding.tvAlbum.text = presenter.album
        binding.tvArtist.text = presenter.artist
        binding.tvDuration.text = presenter.duration.secondsToMinutes()
    }
}