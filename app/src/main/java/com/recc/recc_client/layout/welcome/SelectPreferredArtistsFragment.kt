package com.recc.recc_client.layout.welcome

import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.navigation.fragment.findNavController
import com.recc.recc_client.R
import com.recc.recc_client.databinding.FragmentSelectPreferredArtistsBinding
import com.recc.recc_client.layout.common.BaseFragment
import com.recc.recc_client.layout.common.Event
import com.recc.recc_client.layout.recyclerview.AdapterType
import com.recc.recc_client.layout.recyclerview.DynamicAdapter
import com.recc.recc_client.layout.recyclerview.presenters.ArtistPresenter
import com.recc.recc_client.layout.recyclerview.view_holders.ArtistGridViewHolder
import com.recc.recc_client.utils.SharedPreferences
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class SelectPreferredArtistsFragment : BaseFragment<
        SelectPreferredArtistsScreenEvent,
        SelectPreferredArtistsViewModel,
        FragmentSelectPreferredArtistsBinding>
    (R.layout.fragment_select_preferred_artists) {
    private lateinit var adapter: DynamicAdapter<ArtistPresenter, ArtistGridViewHolder>
    override val viewModel: SelectPreferredArtistsViewModel by viewModel()

    override fun subscribeToViewModel() {
        adapter = DynamicAdapter(AdapterType.GRID_ARTISTS, viewModel)
        binding.rvSelectArtists.adapter = adapter

        binding.btnGotoSelectPreferredTracks?.text = getString(R.string.artists_left_cta, MIN_SELECTED_ARTISTS)

        viewModel.presenterList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        viewModel.selectedItems.observe(viewLifecycleOwner) { selectedArtists ->
            binding.btnGotoSelectPreferredTracks?.background = AppCompatResources.getDrawable(requireContext(), R.drawable.bg_disabled_floating_button)
            when (val left = MIN_SELECTED_ARTISTS - selectedArtists.count()) {
                in 2 .. MIN_SELECTED_ARTISTS -> {
                    binding.btnGotoSelectPreferredTracks?.apply {
                        text = getString(R.string.artists_left_cta, left)
                    }
                }
                1 -> {
                    binding.btnGotoSelectPreferredTracks?.apply {
                        text = getString(R.string.one_artist_left_cta, left)
                    }
                }
                else -> {
                    binding.btnGotoSelectPreferredTracks?.apply {
                        text = getString(R.string.continue_cta)
                        background = AppCompatResources.getDrawable(requireContext(), R.drawable.bg_floating_button)
                    }
                }
            }
        }

        binding.setSearchArtist.onSearch {
            viewModel.searchArtist(it)
        }
        binding.setSearchArtist.onClear {
            viewModel.fetchArtists()
        }

        viewModel.screenEvent.observe(viewLifecycleOwner, Event.EventObserver { screenEvent ->
            when (screenEvent) {
                is SelectPreferredArtistsScreenEvent.ArtistsFetched -> {
                }
                SelectPreferredArtistsScreenEvent.ArtistsNotFetched -> {
                    Toast.makeText(requireContext(), "Artists couldn't get fetched", Toast.LENGTH_SHORT).show()
                }
                SelectPreferredArtistsScreenEvent.GotoHomeBtnClicked -> {
                    findNavController().navigate(R.id.action_selectPreferredArtistsFragment_to_selectPreferredTracksFragment2)
                }
            }
        })
        viewModel.fetchArtists()

        // Sets recyclerview item's color
        viewModel.setSelectedItemColor(requireContext().getColor(R.color.bright_green))
        viewModel.setUnselectedItemColor(requireContext().getColor(R.color.white))
    }
}