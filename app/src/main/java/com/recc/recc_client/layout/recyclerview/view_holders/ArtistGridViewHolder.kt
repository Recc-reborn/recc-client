package com.recc.recc_client.layout.recyclerview.view_holders

import com.bumptech.glide.Glide
import com.recc.recc_client.databinding.FragmentArtistGridItemBinding
import com.recc.recc_client.layout.recyclerview.BaseViewHolder
import com.recc.recc_client.layout.recyclerview.presenters.ArtistPresenter

class ArtistGridViewHolder(private val binding: FragmentArtistGridItemBinding): BaseViewHolder(binding.root) {

    fun bind(presenter: ArtistPresenter) {
        binding.tvArtistName.text = presenter.name
        Glide.with(binding.root)
            .load(presenter.image.last().url)
            .fitCenter()
            .circleCrop()
            .into(binding.ivArtist)
    }
}