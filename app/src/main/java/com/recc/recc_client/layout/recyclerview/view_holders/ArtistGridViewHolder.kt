package com.recc.recc_client.layout.recyclerview.view_holders

import com.bumptech.glide.Glide
import com.recc.recc_client.R
import com.recc.recc_client.databinding.FragmentArtistGridItemBinding
import com.recc.recc_client.layout.recyclerview.BaseViewHolder
import com.recc.recc_client.layout.recyclerview.presenters.ArtistPresenter
import com.recc.recc_client.layout.welcome.WelcomeViewModel

class ArtistGridViewHolder(
    private val binding: FragmentArtistGridItemBinding,
    private val viewModel: WelcomeViewModel
): BaseViewHolder(binding.root) {
    private lateinit var presenter: ArtistPresenter
    private var isSelected = false

    private fun selectItem() {
        binding.ivArtist.setBackgroundResource(R.drawable.bg_artist_item_selected)
        viewModel.selectedItemColor.value?.let {
            binding.tvArtistName.setTextColor(it)
        }
        isSelected = true
        viewModel.addArtist(presenter.lastFmUrl)
    }

    private fun unselectItem() {
        binding.ivArtist.setBackgroundResource(0)
        viewModel.unselectedItemColor.value?.let {
            binding.tvArtistName.setTextColor(it)
        }
        isSelected = false
        viewModel.removeArtist(presenter.lastFmUrl)
    }

    fun bind(presenter: ArtistPresenter) {
        this.presenter = presenter
        binding.tvArtistName.text = presenter.name
        viewModel.selectedArtists.value?.let {
            if (presenter.lastFmUrl in it && !isSelected) {
                selectItem()
            }
        }
        binding.llArtistContainer.setOnClickListener {
            if (isSelected) {
                unselectItem()
            } else {
                selectItem()
            }
        }
        Glide.with(binding.root)
            .load(this.presenter.imageUrl)
            .fitCenter()
            .circleCrop()
            .into(binding.ivArtist)
    }
}