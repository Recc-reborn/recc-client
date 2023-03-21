package com.recc.recc_client.layout.home

import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.navigation.fragment.findNavController
import com.recc.recc_client.R
import com.recc.recc_client.databinding.FragmentSelectCustomTracksBinding
import com.recc.recc_client.layout.common.BaseFragment
import com.recc.recc_client.layout.common.Event
import com.recc.recc_client.layout.common.InteractiveItemsScreenEvent
import com.recc.recc_client.layout.recyclerview.AdapterType
import com.recc.recc_client.layout.recyclerview.DynamicAdapter
import com.recc.recc_client.layout.recyclerview.presenters.TrackPresenter
import com.recc.recc_client.layout.recyclerview.view_holders.TrackListViewHolder
import org.koin.androidx.viewmodel.ext.android.viewModel

class SelectCustomPlaylistTracks
    : BaseFragment<InteractiveItemsScreenEvent, SelectCustomPlaylistTracksViewModel, FragmentSelectCustomTracksBinding>(R.layout.fragment_select_custom_tracks) {
    override val viewModel: SelectCustomPlaylistTracksViewModel by viewModel()
    private lateinit var adapter: DynamicAdapter<TrackPresenter, TrackListViewHolder>

    private fun enableCreateButton() {
        binding.btnGotoHome.apply {
            text = getString(R.string.continue_cta)
            background = AppCompatResources.getDrawable(requireContext(), R.drawable.bg_floating_button)
            if (!binding.vetvPlaylistTitle.getFieldEmpty()) {
                isEnabled = true
            }
        }
    }

    override fun subscribeToViewModel() {
        adapter = DynamicAdapter(AdapterType.LIST_CUSTOM_PLAYLIST_TRACKS, viewModel)
        binding.rvSelectArtists.adapter = adapter

        binding.vetvPlaylistTitle.callback = {
            viewModel.setTitle(binding.vetvPlaylistTitle.getFieldText())
            viewModel.selectedItems.value?.let { selectedItems ->
                if (MIN_SELECTED_TRACKS - selectedItems.count() <= 0) {
                    enableCreateButton()
                }
            }
        }

        viewModel.presenterList.observe(viewLifecycleOwner) {
            adapter.submitList(it.toMutableList())
        }

        binding.btnGotoHome.text = getString(R.string.artists_left_cta, MIN_SELECTED_TRACKS)

        viewModel.selectedItems.observe(viewLifecycleOwner) { selectedArtists ->
            binding.btnGotoHome.background = AppCompatResources.getDrawable(requireContext(), R.drawable.bg_disabled_floating_button)
            when (val left = MIN_SELECTED_TRACKS - selectedArtists.count()) {
                1 -> {
                    binding.btnGotoHome.apply {
                        text = getString(R.string.one_track_left_cta, left)
                        isEnabled = false
                    }
                }
                in Int.MIN_VALUE .. 0 -> {
                    enableCreateButton()
                }
            }
        }

        binding.setSearchArtist.onSearch { viewModel.searchTracks(it) }
        binding.setSearchArtist.onClear { viewModel.fetchTracks() }

        viewModel.screenEvent.observe(viewLifecycleOwner, Event.EventObserver { screenEvent ->
            when (screenEvent) {
                is InteractiveItemsScreenEvent.ItemsFetched -> {
                    viewModel.replaceList(screenEvent.trackPresenterList)
                }
                is InteractiveItemsScreenEvent.ItemsNotFetched -> {
                    Toast.makeText(requireContext(), screenEvent.error, Toast.LENGTH_SHORT).show()
                }
                is InteractiveItemsScreenEvent.SearchingItemsUnsuccessfully -> {
                    Toast.makeText(requireContext(), screenEvent.error, Toast.LENGTH_SHORT).show()
                }
                is InteractiveItemsScreenEvent.FailedAddingItems -> {
                    Toast.makeText(requireContext(), screenEvent.error, Toast.LENGTH_SHORT).show()
                }
                is InteractiveItemsScreenEvent.FailedFetchingNextPage -> {
                    Toast.makeText(requireContext(), screenEvent.error, Toast.LENGTH_SHORT).show()
                }
                InteractiveItemsScreenEvent.ItemsAdded -> {
                    findNavController().popBackStack()
                }
                else -> {}
            }
        })
        viewModel.fetchTracks()

        // Sets recyclerview item's color
        viewModel.setSelectedItemColor(requireContext().getColor(R.color.bright_green))
        viewModel.setUnselectedItemColor(requireContext().getColor(R.color.white))
    }
}