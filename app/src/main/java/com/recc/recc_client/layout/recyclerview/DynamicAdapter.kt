package com.recc.recc_client.layout.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.ListAdapter
import com.recc.recc_client.databinding.*
import com.recc.recc_client.layout.home.HomeViewModel
import com.recc.recc_client.layout.recyclerview.presenters.*
import com.recc.recc_client.layout.recyclerview.view_holders.*
import com.recc.recc_client.layout.welcome.SelectPreferredArtistsViewModel
import com.recc.recc_client.layout.welcome.SelectPreferredTracksViewModel

enum class AdapterType {
    GRID_ARTISTS,
    SWIMLANE_PLAYLIST,
    SWIMLANE_PLAYLIST_TRACKS,
    LIST_TRACKS,
    LIST_PREFERRED_TRACKS,
}

class DynamicAdapter<P: BasePresenter, VH: BaseViewHolder> (
    private val type: AdapterType,
    private val viewModel: ViewModel,
    private val context: Context? = null
) : ListAdapter<P, VH>(DiffUtilCallback<P>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        when(type) {
            AdapterType.GRID_ARTISTS -> {
                val binding = ViewArtistGridItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ArtistGridViewHolder(binding, viewModel as SelectPreferredArtistsViewModel) as VH
            }
            AdapterType.SWIMLANE_PLAYLIST -> {
                val binding = ViewSwimlaneBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return PlaylistSwimlaneViewHolder(binding, viewModel as HomeViewModel) as VH
            }
            AdapterType.SWIMLANE_PLAYLIST_TRACKS -> {
                val binding = ViewSwimlaneTracksBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return TracksSwimlaneViewHolder(binding, viewModel as HomeViewModel) as VH
            }
            AdapterType.LIST_TRACKS -> {
                val binding = ViewTrackListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return TrackListViewHolder(binding, viewModel, true) as VH
            }
            AdapterType.LIST_PREFERRED_TRACKS -> {
                val binding = ViewTrackListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return TrackListViewHolder(binding, viewModel, false) as VH
            }
        }
        throw ClassNotFoundException("The given ViewHolder doesn't exist")
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        if (currentList.isNotEmpty()) {
            when (type) {
                AdapterType.GRID_ARTISTS -> {
                    holder as ArtistGridViewHolder
                    val presenter = currentList[position] as ArtistPresenter
                    if (position == currentList.count() - 1)
                        (viewModel as SelectPreferredArtistsViewModel).fetchNextPage()
                    holder.bind(presenter)
                }
                AdapterType.SWIMLANE_PLAYLIST -> {
                    holder as PlaylistSwimlaneViewHolder
                    val presenter = currentList[position] as PlaylistPresenter
                    holder.bind(presenter)
                }
                AdapterType.LIST_TRACKS -> {
                    holder as TrackListViewHolder
                    val presenter = currentList[position] as TrackPresenter
                    context?.let {
                        holder.bindPlaylistTrack(presenter, it)
                    }
                }
                AdapterType.SWIMLANE_PLAYLIST_TRACKS -> {
                    holder as TracksSwimlaneViewHolder
                    val presenter = currentList[position] as TrackPresenter
                    holder.bind(presenter)
                }
                AdapterType.LIST_PREFERRED_TRACKS -> {
                    holder as TrackListViewHolder
                    val presenter = currentList[position] as TrackPresenter
                    if (position == currentList.count() - 1)
                        (viewModel as SelectPreferredTracksViewModel).fetchNextPage()
                    holder.bindPreferredTrack(presenter, true)
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