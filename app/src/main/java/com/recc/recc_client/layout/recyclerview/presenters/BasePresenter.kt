package com.recc.recc_client.layout.recyclerview.presenters

interface BasePresenter {
    fun areContentsTheSame(other: BasePresenter): Boolean
}