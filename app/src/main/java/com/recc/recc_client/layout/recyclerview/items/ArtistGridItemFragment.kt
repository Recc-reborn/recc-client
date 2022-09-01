package com.recc.recc_client.layout.recyclerview.items

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.recc.recc_client.R
import com.recc.recc_client.databinding.FragmentArtistGridItemBinding

class ArtistGridItemFragment : Fragment() {
    private lateinit var binding: FragmentArtistGridItemBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_artist_grid_item, container, false)

        return binding.root
    }
}