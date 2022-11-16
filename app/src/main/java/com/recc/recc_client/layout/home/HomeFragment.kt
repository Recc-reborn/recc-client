package com.recc.recc_client.layout.home

import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.recc.recc_client.R
import com.recc.recc_client.databinding.FragmentHomeBinding
import com.recc.recc_client.layout.common.BaseFragment
import com.recc.recc_client.layout.common.Event
import com.recc.recc_client.layout.recyclerview.AdapterType
import com.recc.recc_client.layout.recyclerview.DynamicAdapter
import com.recc.recc_client.layout.recyclerview.presenters.PlaylistPresenter
import com.recc.recc_client.layout.recyclerview.view_holders.PlaylistItemViewHolder
import kotlinx.android.synthetic.main.fragment_home.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<HomeScreenEvent, HomeViewModel, FragmentHomeBinding>(R.layout.fragment_home) {
    override val viewModel: HomeViewModel by viewModel()
    private var adapter: DynamicAdapter<PlaylistPresenter, PlaylistItemViewHolder>? = null

    override fun subscribeToViewModel() {
        viewModel.screenEvent.observe(viewLifecycleOwner, Event.EventObserver { screenEvent ->
            when (screenEvent) {
                HomeScreenEvent.PlaylistSelected -> {
                    viewModel.selectedPlaylist.value?.let {
                        val bundle = bundleOf(
                            "image" to it.image,
                            "id" to it.id,
                            "artists" to it.artists,
                            "name" to it.name,
                            "tags" to it.tags,
                            "createdAt" to it.createdAt)
                        findNavController().navigate(R.id.action_pagerFragment_to_playlistSongsFragment, bundle)
                    }
                }
            }
        })

        viewModel.playlists.observe(viewLifecycleOwner) {
            adapter?.submitList(it)
        }

        adapter = DynamicAdapter(AdapterType.PLAYLISTS, viewModel)
        binding.rvHomePlaylist.rv_home_playlist.adapter = adapter
        viewModel.getPlaylists()
    }
}