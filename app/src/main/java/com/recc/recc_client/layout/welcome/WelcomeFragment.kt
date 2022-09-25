package com.recc.recc_client.layout.welcome

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.recc.recc_client.MainActivity
import com.recc.recc_client.R
import com.recc.recc_client.databinding.FragmentWelcomeBinding
import com.recc.recc_client.layout.common.BaseFragment
import com.recc.recc_client.layout.common.Event
import com.recc.recc_client.layout.recyclerview.AdapterType
import com.recc.recc_client.layout.recyclerview.DynamicAdapter
import com.recc.recc_client.layout.recyclerview.presenters.ArtistPresenter
import com.recc.recc_client.layout.recyclerview.view_holders.ArtistGridViewHolder
import com.recc.recc_client.utils.Alert
import kotlinx.android.synthetic.main.fragment_welcome.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class WelcomeFragment : BaseFragment<WelcomeScreenEvent, WelcomeViewModel, FragmentWelcomeBinding>(R.layout.fragment_welcome) {
    override val viewModel: WelcomeViewModel by viewModel()

    override fun subscribeToViewModel() {
        viewModel.getTopArtists()
        viewModel.screenEvent.observe(viewLifecycleOwner, Event.EventObserver { screenEvent ->
            when (screenEvent) {
                is WelcomeScreenEvent.ArtistsFetched -> {
                    val adapter = DynamicAdapter<ArtistPresenter, ArtistGridViewHolder>(AdapterType.ARTISTS_GRID)
                    val presenterList = screenEvent.artist.artists.artist.map { ArtistPresenter(it) }
                    binding.rvSelectArtists.rv_select_artists.adapter = adapter
                    adapter.submitList(presenterList)
                }
                WelcomeScreenEvent.ArtistsNotFetched -> {
                    Toast.makeText(requireContext(), "Artists couldn't get fetched", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}