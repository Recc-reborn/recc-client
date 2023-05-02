package com.recc.recc_client.layout.recyclerview.view_holders

import com.bumptech.glide.Glide
import com.recc.recc_client.R
import com.recc.recc_client.databinding.ViewArtistGridItemBinding
import com.recc.recc_client.layout.recyclerview.presenters.ArtistPresenter
import com.recc.recc_client.layout.welcome.SelectPreferredArtistsTracksViewModel

class ArtistGridViewHolder(
    private val binding: ViewArtistGridItemBinding,
    private val viewModel: SelectPreferredArtistsTracksViewModel
): BaseViewHolder(binding.root) {
    private lateinit var presenter: ArtistPresenter
    private var isSelected = false

    private fun selectItem() {
        binding.ivArtist.setBackgroundResource(R.drawable.bg_artist_item_selected)
        viewModel.selectedItemColor.value?.let {
            binding.tvArtistName.setTextColor(it)
        }
        isSelected = true
        viewModel.addItem(presenter.id)
    }

    private fun unselectItem() {
        binding.ivArtist.setBackgroundResource(0)
        viewModel.unselectedItemColor.value?.let {
            binding.tvArtistName.setTextColor(it)
        }
        isSelected = false
        viewModel.removeItem(presenter.id)
    }

    fun bind(presenter: ArtistPresenter) {
        this.presenter = presenter
        binding.tvArtistName.text = presenter.name
        viewModel.selectedItems.value?.let {
            if (presenter.id in it && !isSelected) {
                selectItem()
            }
        }
        binding.llArtistContainer.setOnClickListener {
            if (isSelected) unselectItem() else selectItem()
        }
        if (presenter.imageUrl.isNotEmpty()) {
            Glide.with(binding.root)
                .load(presenter.imageUrl)
                .fitCenter()
                .circleCrop()
                .into(binding.ivArtist)
        }
    }
}