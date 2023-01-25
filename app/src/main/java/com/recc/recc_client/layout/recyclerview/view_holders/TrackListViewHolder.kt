package com.recc.recc_client.layout.recyclerview.view_holders

import android.view.View
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.recc.recc_client.R
import com.recc.recc_client.databinding.ViewTrackListItemBinding
import com.recc.recc_client.layout.recyclerview.presenters.TrackPresenter
import com.recc.recc_client.layout.welcome.SelectPreferredTracksViewModel
import com.recc.recc_client.utils.Alert
import com.recc.recc_client.utils.millisecondsToMinutes

class TrackListViewHolder(
    private val binding: ViewTrackListItemBinding,
    private val viewModel: ViewModel,
    private val showMenu: Boolean
): BaseViewHolder(binding.root) {

    fun bindPreferredList(presenter: TrackPresenter, isInteractive: Boolean = false) {
        val vm = viewModel as SelectPreferredTracksViewModel
        if (isInteractive)
            handleSelection(presenter, vm)
        bind(presenter)
    }

    fun bindPlaylist(presenter: TrackPresenter) {
        bind(presenter)
    }

    private fun handleSelection(presenter: TrackPresenter, vm: SelectPreferredTracksViewModel) {
        val hasBeenSelected = vm.selectedItems.value?.contains(presenter.id)
        // select if not previously selected
        binding.clContainer.setOnClickListener {
            if (hasBeenSelected != null && hasBeenSelected) {
                binding.clContainer.setBackgroundResource(R.drawable.bg_playlist_item)
                vm.removeItem(presenter.id)
            } else {
                binding.clContainer.setBackgroundResource(R.drawable.bg_playlist_item_selected)
                vm.addItem(presenter.id)
            }
        }
        if (hasBeenSelected != null && hasBeenSelected) {
            binding.clContainer.setBackgroundResource(R.drawable.bg_playlist_item_selected)
        } else {
            binding.clContainer.setBackgroundResource(R.drawable.bg_playlist_item)
        }
    }

    private fun bind(presenter: TrackPresenter) {
        binding.ivMenu.visibility = if (showMenu) View.VISIBLE else View.GONE
        Glide.with(binding.ivAlbumImage)
            .load(presenter.albumArtUrl)
            .fitCenter()
            .into(binding.ivAlbumImage)
        binding.tvTrackTitle.text = presenter.title
        binding.tvAlbum.text = presenter.album
        binding.tvArtist.text = presenter.artist

        binding.tvDuration.text = presenter.duration.millisecondsToMinutes()
    }
}