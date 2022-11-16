package com.recc.recc_client.layout.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.ListAdapter
import com.recc.recc_client.databinding.FragmentArtistGridItemBinding
import com.recc.recc_client.databinding.FragmentPlaylistItemBinding
import com.recc.recc_client.databinding.FragmentSongItemBinding
import com.recc.recc_client.layout.home.HomeViewModel
import com.recc.recc_client.layout.playlist.PlaylistViewModel
import com.recc.recc_client.layout.recyclerview.view_holders.ArtistGridViewHolder
import com.recc.recc_client.layout.recyclerview.presenters.ArtistPresenter
import com.recc.recc_client.layout.recyclerview.presenters.BasePresenter
import com.recc.recc_client.layout.recyclerview.presenters.PlaylistPresenter
import com.recc.recc_client.layout.recyclerview.presenters.SongPresenter
import com.recc.recc_client.layout.recyclerview.view_holders.BaseViewHolder
import com.recc.recc_client.layout.recyclerview.view_holders.PlaylistItemViewHolder
import com.recc.recc_client.layout.recyclerview.view_holders.SongViewHolder
import com.recc.recc_client.layout.welcome.WelcomeViewModel

enum class AdapterType {
    ARTISTS_GRID,
    PLAYLISTS,
    SONGS
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
            AdapterType.PLAYLISTS -> {
                val binding = FragmentPlaylistItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return PlaylistItemViewHolder(binding, viewModel as HomeViewModel) as VH
            }
            AdapterType.SONGS -> {
                val binding = FragmentSongItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return SongViewHolder(binding, viewModel as PlaylistViewModel) as VH
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
                AdapterType.PLAYLISTS -> {
                    holder as PlaylistItemViewHolder
                    val presenter = currentList[position] as PlaylistPresenter
                    holder.bind(presenter)
                }
                AdapterType.SONGS -> {
                    holder as SongViewHolder
                    val presenter = currentList[position] as SongPresenter
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