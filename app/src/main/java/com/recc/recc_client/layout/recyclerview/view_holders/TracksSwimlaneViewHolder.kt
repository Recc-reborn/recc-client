package com.recc.recc_client.layout.recyclerview.view_holders

import com.bumptech.glide.Glide
import com.recc.recc_client.databinding.ViewSwimlaneTracksBinding
import com.recc.recc_client.layout.home.HomeViewModel
import com.recc.recc_client.layout.recyclerview.presenters.TrackPresenter
import com.recc.recc_client.utils.Alert

class TracksSwimlaneViewHolder(
    private val binding: ViewSwimlaneTracksBinding,
    private val viewModel: HomeViewModel
): BaseViewHolder(binding.root) {

    fun bind(presenter: TrackPresenter) {
        binding.tvTrackTitle.text = presenter.title
        if (presenter.albumArtUrl.isNotEmpty()) {
            Alert("presenter: $presenter")
            Glide.with(binding.ivAlbumImage)
                .load(presenter.albumArtUrl)
                .fitCenter()
                .into(binding.ivAlbumImage)
        }
        binding.tvArtist.text = presenter.artist
    }
}