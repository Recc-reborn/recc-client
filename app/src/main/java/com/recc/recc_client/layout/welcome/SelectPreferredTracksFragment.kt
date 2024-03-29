package com.recc.recc_client.layout.welcome

import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.navigation.fragment.findNavController
import com.recc.recc_client.R
import com.recc.recc_client.databinding.FragmentSelectPreferredTracksBinding
import com.recc.recc_client.layout.common.BaseFragment
import com.recc.recc_client.layout.common.Event
import com.recc.recc_client.layout.common.InteractiveItemsScreenEvent
import com.recc.recc_client.layout.recyclerview.AdapterType
import com.recc.recc_client.layout.recyclerview.DynamicAdapter
import com.recc.recc_client.layout.recyclerview.presenters.TrackPresenter
import com.recc.recc_client.layout.recyclerview.view_holders.TrackListViewHolder
import org.koin.androidx.viewmodel.ext.android.viewModel

class SelectPreferredTracksFragment
    : BaseFragment<InteractiveItemsScreenEvent, SelectPreferredTracksViewModel, FragmentSelectPreferredTracksBinding>(R.layout.fragment_select_preferred_tracks) {
    override val viewModel: SelectPreferredTracksViewModel by viewModel()
    private lateinit var adapter: DynamicAdapter<TrackPresenter, TrackListViewHolder>

    override fun subscribeToViewModel() {
        adapter = DynamicAdapter(AdapterType.LIST_PREFERRED_TRACKS, viewModel)
        binding.rvSelectArtists.adapter = adapter

        viewModel.presenterList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        binding.btnGotoHome.text = getString(R.string.tracks_left_cta, MIN_SELECTED_TRACKS)

        viewModel.selectedItems.observe(viewLifecycleOwner) { selectedArtists ->
            binding.btnGotoHome.background = AppCompatResources.getDrawable(requireContext(), R.drawable.bg_disabled_floating_button)
            when (val left = MIN_SELECTED_TRACKS - selectedArtists.count()) {
                in 2 .. MIN_SELECTED_TRACKS -> {
                    binding.btnGotoHome.text = getString(R.string.tracks_left_cta, left)
                }
                1 -> {
                    binding.btnGotoHome.text = getString(R.string.one_track_left_cta, left)
                }
                in Int.MIN_VALUE .. 0 -> {
                    binding.btnGotoHome.apply {
                        text = getString(R.string.continue_cta)
                        background = AppCompatResources.getDrawable(requireContext(), R.drawable.bg_floating_button)
                    }
                    binding.btnGotoHome.isEnabled = true
                }
            }
        }

        binding.setSearchArtist.onSearch {
            viewModel.searchTracks(it)
        }
        binding.setSearchArtist.onClear {
            viewModel.fetchTracks()
        }

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
                    findNavController().navigate(R.id.action_selectPreferredTracksFragment2_to_pagerFragment)
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