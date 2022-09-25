package com.recc.recc_client.layout.recyclerview.items

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.recc.recc_client.R
import com.recc.recc_client.databinding.FragmentArtistGridItemBinding
import com.recc.recc_client.utils.Alert

class ArtistGridItemFragment : Fragment() {
    private lateinit var binding: FragmentArtistGridItemBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_artist_grid_item, container, false)
        Alert("Creating artist item")
        selectItem()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        selectItem()
    }

    fun selectItem() {
        Alert("setting background")
        binding.llArtistContainer.setBackgroundResource(R.drawable.bg_artist_item_selected)
    }

    fun unselectItem() {
        binding.ivArtist.background = null
    }
}