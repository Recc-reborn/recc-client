package com.recc.recc_client.layout.recyclerview.view_holders

import com.recc.recc_client.databinding.FragmentTrackSwimlaneBinding
import com.recc.recc_client.layout.home.HomeViewModel
import com.recc.recc_client.layout.recyclerview.AdapterType
import com.recc.recc_client.layout.recyclerview.DynamicAdapter
import com.recc.recc_client.layout.recyclerview.presenters.PlaylistPresenter
import com.recc.recc_client.layout.recyclerview.presenters.TrackPresenter
import com.recc.recc_client.utils.Alert

private const val MAX_TRACK_PER_SWIMLANE = 10

class TrackSwimlaneViewHolder(
    private val binding: FragmentTrackSwimlaneBinding,
    private val viewModel: HomeViewModel
): BaseViewHolder(binding.root) {

    private val adapter = DynamicAdapter<TrackPresenter, TrackSwimlaneItemViewHolder>(
        AdapterType.TRACKS_SWIMLANE_ITEMS,
        viewModel)

    fun bind(presenter: PlaylistPresenter) {
        binding.tvTitle.text = presenter.title
        binding.rvTracks.adapter = adapter
        if (presenter.tracks.size > MAX_TRACK_PER_SWIMLANE) {
            adapter.submitList(presenter.tracks.subList(0, MAX_TRACK_PER_SWIMLANE))
        }

        // listeners
        binding.clTitleContainer.setOnClickListener {
            // TODO: navigates to PlaylistView
        }
    }
}