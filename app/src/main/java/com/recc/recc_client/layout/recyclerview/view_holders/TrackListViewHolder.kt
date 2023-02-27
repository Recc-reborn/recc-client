package com.recc.recc_client.layout.recyclerview.view_holders

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.PopupMenu
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.recc.recc_client.R
import com.recc.recc_client.ReccApplication
import com.recc.recc_client.databinding.ViewTrackListItemBinding
import com.recc.recc_client.layout.recyclerview.presenters.TrackPresenter
import com.recc.recc_client.layout.welcome.SelectPreferredTracksViewModel
import com.recc.recc_client.utils.Alert
import com.recc.recc_client.utils.millisecondsToMinutes

class TrackListViewHolder(
    private val binding: ViewTrackListItemBinding,
    private val viewModel: ViewModel,
    private val isMenuShown: Boolean
): BaseViewHolder(binding.root) {

    fun bindPreferredList(presenter: TrackPresenter, isInteractive: Boolean = false) {
        val vm = viewModel as SelectPreferredTracksViewModel
        if (isInteractive)
            handleSelection(presenter, vm)
        bind(presenter)
    }

    fun bindPlaylist(presenter: TrackPresenter, context: Context) {
        if (isMenuShown) showMenu(context) else binding.tvMenu.visibility = View.GONE
        bind(presenter)
    }

    private fun handleSelection(presenter: TrackPresenter, vm: SelectPreferredTracksViewModel) {
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

    private fun showMenu(context: Context) {
        binding.tvMenu.visibility = View.VISIBLE
        binding.tvMenu.setOnClickListener {
            val menu = PopupMenu(context, binding.tvMenu)
            menu.inflate(R.menu.track_popup_menu)
            menu.setOnMenuItemClickListener { menuItem ->
                when(menuItem.itemId) {
                    R.id.action_open_with_spotify -> {
                        val intent = Intent(Intent.ACTION_VIEW)
                        // TODO: store uris on server and use them here
                        intent.data = Uri.parse("spotify:track:7svpAkwc6xaSxlbZ7V7JiS")
                        intent.putExtra(Intent.EXTRA_REFERRER,
                            Uri.parse("android-app://" + context.packageName))
                        context.startActivity(intent)
                        true
                    }
                    else -> { false }
                }
            }
            menu.show()
        }
    }

    private fun bind(presenter: TrackPresenter) {
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