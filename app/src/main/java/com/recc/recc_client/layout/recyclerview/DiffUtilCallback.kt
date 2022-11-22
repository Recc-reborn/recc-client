package com.recc.recc_client.layout.recyclerview

import androidx.recyclerview.widget.DiffUtil.ItemCallback
import com.recc.recc_client.layout.recyclerview.presenters.BasePresenter

class DiffUtilCallback<P: BasePresenter>: ItemCallback<P>() {
    override fun areItemsTheSame(oldItem: P, newItem: P): Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: P, newItem: P): Boolean = oldItem.areContentsTheSame(newItem)
}