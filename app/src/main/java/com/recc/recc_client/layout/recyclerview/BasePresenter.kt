package com.recc.recc_client.layout.recyclerview

abstract class BasePresenter {
    abstract val viewId: Int

    abstract fun areContentsTheSame(other: BasePresenter): Boolean
}