package com.recc.recc_client.layout.recyclerview.view_holders

import com.bumptech.glide.Glide
import com.recc.recc_client.databinding.FragmentPlaylistItemBinding
import com.recc.recc_client.layout.home.HomeViewModel
import com.recc.recc_client.layout.recyclerview.presenters.PlaylistPresenter

class PlaylistItemViewHolder(
    private val binding: FragmentPlaylistItemBinding,
    private val viewModel: HomeViewModel
): BaseViewHolder(binding.root) {

    fun bind(presenter: PlaylistPresenter) {
        binding.tvCreatedAt.text = presenter.createdAt
        binding.tvName.text = presenter.name
        binding.tvTags.text = presenter.tags.joinToString { "$it " }
        Glide.with(binding.ivImage)
            .load(presenter.image)
            .into(binding.ivImage)
    }
}