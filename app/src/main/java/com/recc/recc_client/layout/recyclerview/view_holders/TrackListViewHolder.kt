package com.recc.recc_client.layout.recyclerview.view_holders

import android.content.Context
import android.view.View
import android.widget.PopupMenu
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.recc.recc_client.R
import com.recc.recc_client.databinding.ViewTrackListItemBinding
import com.recc.recc_client.layout.common.InteractiveTracksViewModel
import com.recc.recc_client.layout.playlist.PlaylistViewModel
import com.recc.recc_client.layout.recyclerview.presenters.TrackPresenter
import com.recc.recc_client.utils.Alert
import com.recc.recc_client.utils.millisecondsToMinutes

class TrackListViewHolder(
    private val binding: ViewTrackListItemBinding,
    private val viewModel: ViewModel,
    private val isMenuShown: Boolean
): BaseViewHolder(binding.root) {

    fun bindPreferredTrack(presenter: TrackPresenter, isInteractive: Boolean = false) {
        val vm = viewModel as InteractiveTracksViewModel<*>
        if (isInteractive)
            handleSelection(presenter, vm)
        bind(presenter)
    }

    fun bindPlaylistTrack(presenter: TrackPresenter, context: Context) {
        if (isMenuShown)
            showMenu(context, presenter)
        else
            binding.tvMenu.visibility = View.GONE
        bind(presenter)
    }

    private fun handleSelection(presenter: TrackPresenter, vm: InteractiveTracksViewModel<*>) {
        // select if not previously selected
        binding.clContainer.setOnClickListener {
            val hasBeenSelected = vm.selectedItems.value?.contains(presenter.id)
            if (hasBeenSelected != null && hasBeenSelected) {
                binding.clContainer.setBackgroundResource(R.drawable.bg_playlist_item)
                vm.removeItem(presenter.id)
            } else {
                binding.clContainer.setBackgroundResource(R.drawable.bg_playlist_item_selected)
                vm.addItem(presenter.id)
            }
        }
        val hasBeenSelected = vm.selectedItems.value?.contains(presenter.id)
        if (hasBeenSelected != null && hasBeenSelected) {
            binding.clContainer.setBackgroundResource(R.drawable.bg_playlist_item_selected)
        } else {
            binding.clContainer.setBackgroundResource(R.drawable.bg_playlist_item)
        }
    }

    private fun showMenu(context: Context, presenter: TrackPresenter) {
        binding.tvMenu.visibility = View.VISIBLE
        binding.tvMenu.setOnClickListener {
            val menu = PopupMenu(context, binding.tvMenu)
            (viewModel as PlaylistViewModel).handleMenuPressed(menu, presenter)
        }
    }

    private fun bind(presenter: TrackPresenter) {
        if (presenter.albumArtUrl.isNotEmpty()) {
            Glide.with(binding.ivAlbumImage)
                .load(presenter.albumArtUrl)
                .fitCenter()
                .into(binding.ivAlbumImage)
        }
        binding.tvTrackTitle.text = presenter.title
        binding.tvAlbum.text = presenter.album
        binding.tvArtist.text = presenter.artist

        binding.tvDuration.text = presenter.duration.millisecondsToMinutes()
    }
}