package com.recc.recc_client.layout.recyclerview.presenters

abstract class BasePresenter {
    abstract fun areContentsTheSame(other: BasePresenter): Boolean
}