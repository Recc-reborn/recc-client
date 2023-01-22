package com.recc.recc_client.layout.welcome

import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.navigation.fragment.findNavController
import com.recc.recc_client.R
import com.recc.recc_client.databinding.FragmentWelcomeBinding
import com.recc.recc_client.layout.common.BaseFragment
import com.recc.recc_client.layout.common.Event
import com.recc.recc_client.layout.recyclerview.AdapterType
import com.recc.recc_client.layout.recyclerview.DynamicAdapter
import com.recc.recc_client.layout.recyclerview.presenters.ArtistPresenter
import com.recc.recc_client.layout.recyclerview.view_holders.ArtistGridViewHolder
import kotlinx.android.synthetic.main.fragment_welcome.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class WelcomeFragment : BaseFragment<WelcomeScreenEvent, WelcomeViewModel, FragmentWelcomeBinding>(R.layout.fragment_welcome) {
    override val viewModel: WelcomeViewModel by viewModel()
    private var adapter: DynamicAdapter<ArtistPresenter, ArtistGridViewHolder>? = null

    override fun subscribeToViewModel() {
        getToken()?.let {
            viewModel.setToken(it)
        }
        binding.btnGotoSelectPreferredTracks?.text = getString(R.string.artists_left_cta, MIN_SELECTED_ARTISTS)

        viewModel.presenterList.observe(viewLifecycleOwner) {
            adapter?.submitList(it)
        }

        viewModel.selectedArtists.observe(viewLifecycleOwner) { selectedArtists ->
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
                        text = getString(R.string.go_to_home_cta)
                        background = AppCompatResources.getDrawable(requireContext(), R.drawable.bg_floating_button)
                    }
                }
            }
        }

        viewModel.screenEvent.observe(viewLifecycleOwner, Event.EventObserver { screenEvent ->
            when (screenEvent) {
                is WelcomeScreenEvent.ArtistsFetched -> {
                    adapter = DynamicAdapter(AdapterType.ARTISTS_GRID, viewModel)
                    binding.rvSelectArtists.rv_select_artists.adapter = adapter

                    binding.setSearchArtist.onSearch {
                        viewModel.searchArtist(it)
                    }
                    binding.setSearchArtist.onClear {
                        viewModel.getTopArtists()
                    }
                }
                WelcomeScreenEvent.ArtistsNotFetched -> {
                    Toast.makeText(requireContext(), "Artists couldn't get fetched", Toast.LENGTH_SHORT).show()
                }
                WelcomeScreenEvent.GotoHomeBtnClicked -> {
                    findNavController().navigate(R.id.action_welcomeFragment_to_homeFragment)
                }
            }
        })
        viewModel.getTopArtists()

        // Sets recyclerview item's color
        viewModel.setSelectedItemColor(requireContext().getColor(R.color.bright_green))
        viewModel.setUnselectedItemColor(requireContext().getColor(R.color.white))
    }
}