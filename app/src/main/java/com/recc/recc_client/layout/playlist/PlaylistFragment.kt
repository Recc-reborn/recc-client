package com.recc.recc_client.layout.playlist

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.recc.recc_client.R
import com.recc.recc_client.WebViewActivity
import com.recc.recc_client.databinding.FragmentPlaylistBinding
import com.recc.recc_client.layout.common.BaseFragment
import com.recc.recc_client.layout.common.Event
import com.recc.recc_client.layout.recyclerview.AdapterType
import com.recc.recc_client.layout.recyclerview.DynamicAdapter
import com.recc.recc_client.layout.recyclerview.presenters.TrackPresenter
import com.recc.recc_client.layout.recyclerview.view_holders.TrackListViewHolder
import com.recc.recc_client.utils.callSpotifyUri
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlaylistFragment: BaseFragment<PlaylistScreenEvent, PlaylistViewModel, FragmentPlaylistBinding>(R.layout.fragment_playlist) {
    override val viewModel: PlaylistViewModel by viewModel()
    private var adapter: DynamicAdapter<TrackPresenter, TrackListViewHolder>? = null
    private var playlistId = 0

    override fun onResume() {
        super.onResume()
        if (playlistId == 0) {
            arguments?.getInt("id")?.let {
                playlistId = it
            }
        }
        viewModel.getTracks(playlistId)
    }

    override fun subscribeToViewModel() {
        viewModel.tracks.observe(viewLifecycleOwner) {
            adapter?.submitList(it)
        }
        viewModel.screenEvent.observe(viewLifecycleOwner, Event.EventObserver { screenEvent ->
            when (screenEvent) {
                is PlaylistScreenEvent.ErrorFetchingTracks -> {
                    Toast.makeText(context, screenEvent.error, Toast.LENGTH_SHORT).show()
                }
                is PlaylistScreenEvent.GoToSpotifyTrack -> {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.callSpotifyUri(requireContext(), screenEvent.presenter.uri)
                }
                is PlaylistScreenEvent.ErrorSearchingTrack -> {
                    val intent = Intent(requireContext(), WebViewActivity::class.java)
                    startActivity(intent)
                }
            }
        })

        // Sets playlist image
        arguments?.getString("title")?.let {
            binding.tvTitle.text = it
        }
        adapter = DynamicAdapter(AdapterType.LIST_TRACKS, viewModel, requireContext())
        binding.rvSongs.adapter = adapter
    }
}