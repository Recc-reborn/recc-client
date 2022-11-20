package com.recc.recc_client.layout.recyclerview.view_holders

import com.bumptech.glide.Glide
import com.recc.recc_client.databinding.FragmentTrackItemBinding
import com.recc.recc_client.layout.playlist.PlaylistViewModel
import com.recc.recc_client.layout.recyclerview.presenters.TrackPresenter
import com.recc.recc_client.utils.secondsToMinutes

class TrackViewHolder(
    private val binding: FragmentTrackItemBinding,
    private val viewModel: PlaylistViewModel
): BaseViewHolder(binding.root) {

    fun bind(presenter: TrackPresenter) {
        Glide.with(binding.ivAlbumImage)
            .load(presenter.albumArtUrl)
            .fitCenter()
            .into(binding.ivAlbumImage)
        binding.tvTrackTitle.text = presenter.title
        // TODO
//        binding.tvAlbum.text = presenter.album
        binding.tvArtist.text = presenter.artist
        binding.tvDuration.text = presenter.duration.secondsToMinutes()
    }
}