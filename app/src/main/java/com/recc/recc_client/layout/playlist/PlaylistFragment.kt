package com.recc.recc_client.layout.playlist

import com.recc.recc_client.R
import com.recc.recc_client.databinding.FragmentPlaylistBinding
import com.recc.recc_client.layout.common.BaseFragment
import com.recc.recc_client.layout.recyclerview.AdapterType
import com.recc.recc_client.layout.recyclerview.DynamicAdapter
import com.recc.recc_client.layout.recyclerview.presenters.TrackPresenter
import com.recc.recc_client.layout.recyclerview.view_holders.TrackListViewHolder
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlaylistFragment: BaseFragment<PlaylistScreenEvent, PlaylistViewModel, FragmentPlaylistBinding>(R.layout.fragment_playlist) {
    override val viewModel: PlaylistViewModel by viewModel()
    private var adapter: DynamicAdapter<TrackPresenter, TrackListViewHolder>? = null

    override fun onResume() {
        super.onResume()
        viewModel.getSongs()
    }

    override fun subscribeToViewModel() {
        viewModel.songs.observe(viewLifecycleOwner) {
            adapter?.submitList(it)
        }
        // Sets playlist image
        arguments?.getString("title")?.let {
            binding.tvTitle.text = it
        }
        adapter = DynamicAdapter(AdapterType.LIST_TRACKS, viewModel)
        binding.rvSongs.adapter = adapter
    }
}