package com.recc.recc_client.layout.recyclerview.view_holders

import android.app.ActionBar.LayoutParams
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.children
import com.bumptech.glide.Glide
import com.recc.recc_client.databinding.FragmentPlaylistItemBinding
import com.recc.recc_client.layout.home.HomeViewModel
import com.recc.recc_client.layout.recyclerview.presenters.PlaylistPresenter
import com.recc.recc_client.utils.Alert
import com.recc.recc_client.utils.GenreFactory

class PlaylistItemViewHolder(
    private val binding: FragmentPlaylistItemBinding,
    private val viewModel: HomeViewModel
): BaseViewHolder(binding.root) {

    fun bind(presenter: PlaylistPresenter) {
        binding.tvCreatedAt.text = presenter.createdAt
        binding.tvName.text = presenter.name
        Glide.with(binding.ivImage)
            .load(presenter.image)
            .fitCenter()
            .into(binding.ivImage)

        // TODO: Use real data
        val genres = GenreFactory.generate(5)
        // Removes existing tags so that won't have to worry about duplicates
        binding.fbTags.removeAllViews()
        for (genre in genres) {
            val textView = TextView(binding.root.context)
            val layoutParams = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
            layoutParams.setMargins(0, 0, 8, 0)
            textView.layoutParams = layoutParams
            textView.text = genre

            binding.fbTags.addView(textView)
        }
    }
}