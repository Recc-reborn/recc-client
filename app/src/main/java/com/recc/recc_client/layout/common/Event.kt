package com.recc.recc_client.layout.common

import androidx.lifecycle.Observer

/**
 * [Event] used as a way of guaranteeing that ViewModel's value is changed only once
 */
class Event<out T>(private val content: T) {
    var hasBeenHandled = false
        private set

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content

    /**
     * An [EventObserver] for [Event]s, simplifying the pattern of checking if the [Event]'s content has
     * already been handled.
     *
     * [onEventUnhandled] is *only* called if the [Event]'s contents has not been handled.
     */
    class EventObserver<T>(private val onEventUnhandled: (T) -> Unit): Observer<Event<T>> {
        override fun onChanged(event: Event<T>?) {
            event?.getContentIfNotHandled()?.let {
                onEventUnhandled(it)
            }
        }
    }
}