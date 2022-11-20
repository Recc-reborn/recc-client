package com.recc.recc_client.layout.home

import com.recc.recc_client.R
import com.recc.recc_client.databinding.FragmentHomeBinding
import com.recc.recc_client.layout.common.BaseFragment
import com.recc.recc_client.layout.recyclerview.AdapterType
import com.recc.recc_client.layout.recyclerview.DynamicAdapter
import com.recc.recc_client.layout.recyclerview.presenters.PlaylistPresenter
import com.recc.recc_client.layout.recyclerview.view_holders.TrackSwimlaneViewHolder
import kotlinx.android.synthetic.main.fragment_home.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<HomeScreenEvent, HomeViewModel, FragmentHomeBinding>(R.layout.fragment_home) {
    override val viewModel: HomeViewModel by viewModel()
    private var adapter: DynamicAdapter<PlaylistPresenter, TrackSwimlaneViewHolder>? = null

    override fun subscribeToViewModel() {
        viewModel.playlists.observe(viewLifecycleOwner) {
            adapter?.submitList(it)
        }
        adapter = DynamicAdapter(AdapterType.TRAK_SWIMLANE, viewModel)
        binding.rvHomePlaylist.rv_home_playlist.adapter = adapter
        viewModel.getPlaylists()
    }
}