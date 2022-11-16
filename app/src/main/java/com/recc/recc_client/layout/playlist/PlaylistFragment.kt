package com.recc.recc_client.layout.playlist

import android.app.ActionBar
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.recc.recc_client.R
import com.recc.recc_client.databinding.FragmentPlaylistSongsBinding
import com.recc.recc_client.layout.common.BaseFragment
import com.recc.recc_client.layout.recyclerview.AdapterType
import com.recc.recc_client.layout.recyclerview.DynamicAdapter
import com.recc.recc_client.layout.recyclerview.presenters.SongPresenter
import com.recc.recc_client.layout.recyclerview.view_holders.SongViewHolder
import com.recc.recc_client.utils.GenreFactory
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlaylistFragment: BaseFragment<PlaylistScreenEvent, PlaylistViewModel, FragmentPlaylistSongsBinding>(R.layout.fragment_playlist_songs) {
    override val viewModel: PlaylistViewModel by viewModel()
    private var adapter: DynamicAdapter<SongPresenter, SongViewHolder>? = null

    override fun onResume() {
        super.onResume()
        viewModel.getSongs()
    }

    override fun subscribeToViewModel() {
        viewModel.songs.observe(viewLifecycleOwner) {
            adapter?.submitList(it)
        }
        // Sets playlist image
        arguments?.getString("image")?.let {
            Glide.with(binding.ivImage)
                .load(it)
                .fitCenter()
                .into(binding.ivImage)
        }
        arguments?.getString("name")?.let {
            binding.tvTitle.text = it
        }
        arguments?.getString("createdAt")?.let {
            binding.tvDate.text = it
        }
        // Generates random tags that get added to fbTags
        val genres = GenreFactory.generate(7)
        // Removes existing tags so that won't have to worry about duplicates
        binding.fbTags.removeAllViews()
        for (genre in genres) {
            val textView = TextView(binding.root.context)
            val layoutParams = LinearLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT)
            layoutParams.setMargins(0, 0, 8, 0)
            textView.setTextAppearance(R.style.InfoBox_Subtext)
            textView.layoutParams = layoutParams
            textView.text = genre
            binding.fbTags.addView(textView)
        }
        adapter = DynamicAdapter(AdapterType.SONGS, viewModel)
        binding.rvSongs.adapter = adapter
    }
}