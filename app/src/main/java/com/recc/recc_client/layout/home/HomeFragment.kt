package com.recc.recc_client.layout.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.recc.recc_client.R
import com.recc.recc_client.databinding.FragmentHomeBinding
import com.recc.recc_client.layout.common.BaseFragment
import com.recc.recc_client.layout.recyclerview.AdapterType
import com.recc.recc_client.layout.recyclerview.DynamicAdapter
import com.recc.recc_client.layout.recyclerview.presenters.PlaylistPresenter
import com.recc.recc_client.layout.recyclerview.view_holders.PlaylistItemViewHolder
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<HomeScreenEvent, HomeViewModel, FragmentHomeBinding>(R.layout.fragment_home) {
    override val viewModel: HomeViewModel by viewModel()
    private var adapter: DynamicAdapter<PlaylistPresenter, PlaylistItemViewHolder>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun subscribeToViewModel() {
        viewModel.playlists.observe(viewLifecycleOwner) {
            adapter?.submitList(it)
        }
        adapter = DynamicAdapter(AdapterType.PLAYLISTS, viewModel)
        binding.rvHomePlaylists.adapter = adapter

    }
}