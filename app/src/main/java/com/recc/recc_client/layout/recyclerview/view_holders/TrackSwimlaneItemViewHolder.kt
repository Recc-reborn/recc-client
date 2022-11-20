package com.recc.recc_client.layout.recyclerview.view_holders

import com.recc.recc_client.databinding.FragmentTrackSwimlaneItemBinding
import com.recc.recc_client.layout.home.HomeViewModel
import com.recc.recc_client.layout.recyclerview.presenters.TrackPresenter

class TrackSwimlaneItemViewHolder(
    private val binding: FragmentTrackSwimlaneItemBinding,
    private val viewModel: HomeViewModel
): BaseViewHolder(binding.root) {

    fun bind(presenter: TrackPresenter) {

    }
}