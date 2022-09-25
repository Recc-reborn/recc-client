package com.recc.recc_client.layout.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.recc.recc_client.R
import com.recc.recc_client.databinding.FragmentArtistGridItemBinding
import com.recc.recc_client.layout.recyclerview.view_holders.ArtistGridViewHolder
import com.recc.recc_client.layout.recyclerview.items.ArtistGridItemFragment
import com.recc.recc_client.layout.recyclerview.presenters.ArtistPresenter
import com.recc.recc_client.models.last_fm.Artist
import java.io.Serializable

enum class AdapterType {
    ARTISTS_GRID
}

class DynamicAdapter<P: BasePresenter, VH: BaseViewHolder> (private val type: AdapterType)
    : ListAdapter<P, VH>(DiffUtilCallback<P>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        when(type) {
            AdapterType.ARTISTS_GRID -> {
                val binding = FragmentArtistGridItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return ArtistGridViewHolder(binding) as VH
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
                    holder.bind(presenter)
                }
            }
        }
    }

    override fun getItemCount(): Int = currentList.count()
}