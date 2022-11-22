package com.recc.recc_client.layout.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.ListAdapter
import com.recc.recc_client.databinding.FragmentArtistGridItemBinding
import com.recc.recc_client.databinding.FragmentTrackItemBinding
import com.recc.recc_client.databinding.FragmentTrackSwimlaneBinding
import com.recc.recc_client.databinding.FragmentTrackSwimlaneItemBinding
import com.recc.recc_client.layout.home.HomeViewModel
import com.recc.recc_client.layout.playlist.PlaylistViewModel
import com.recc.recc_client.layout.recyclerview.presenters.*
import com.recc.recc_client.layout.recyclerview.view_holders.*
import com.recc.recc_client.layout.welcome.WelcomeViewModel

enum class AdapterType {
    ARTISTS_GRID,
    TRACK_SWIMLANE,
    TRACKS_SWIMLANE_ITEMS,
    TRACKS,
}

class DynamicAdapter<P: BasePresenter, VH: BaseViewHolder> (
    private val type: AdapterType,
    private val viewModel: ViewModel
) : ListAdapter<P, VH>(DiffUtilCallback<P>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        when(type) {
            AdapterType.ARTISTS_GRID -> {
                val binding = FragmentArtistGridItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return ArtistGridViewHolder(binding, viewModel as WelcomeViewModel) as VH
            }
            AdapterType.TRACK_SWIMLANE -> {
                val binding = FragmentTrackSwimlaneBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return TrackSwimlaneViewHolder(binding, viewModel as HomeViewModel) as VH
            }
            AdapterType.TRACKS_SWIMLANE_ITEMS -> {
                val binding = FragmentTrackSwimlaneItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return TrackSwimlaneItemViewHolder(binding, viewModel as HomeViewModel) as VH
            }
            AdapterType.TRACKS -> {
                val binding = FragmentTrackItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return TrackViewHolder(binding, viewModel as PlaylistViewModel) as VH
            }
        }
        throw ClassNotFoundException("The given ViewHolder doesn't exist")
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        if (currentList.isNotEmpty()) {
            when (type) {
                AdapterType.ARTISTS_GRID -> {
                    holder as ArtistGridViewHolder
                    val presenter = currentList[position] as ArtistPresenter
                    if (position == currentList.count() - 1) {
                        (viewModel as WelcomeViewModel).getNextPage()
                    }
                    holder.bind(presenter)
                }
                AdapterType.TRACK_SWIMLANE -> {
                    holder as TrackSwimlaneViewHolder
                    val presenter = currentList[position] as PlaylistPresenter
                    holder.bind(presenter)
                }
                AdapterType.TRACKS -> {
                    holder as TrackViewHolder
                    val presenter = currentList[position] as TrackPresenter
                    holder.bind(presenter)
                }
                AdapterType.TRACKS_SWIMLANE_ITEMS -> {
                    holder as TrackSwimlaneItemViewHolder
                    val presenter = currentList[position] as TrackPresenter
                    holder.bind(presenter)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    fun clearList() {
        currentList.clear()
        notifyDataSetChanged()
    }
}