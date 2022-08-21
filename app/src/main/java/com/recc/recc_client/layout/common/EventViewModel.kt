package com.recc.recc_client.layout.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * @param T ScreenEvent that has the events compatible with this [ViewModel]
 * Base [ViewModel], it implements a [screenEvent] that has a custom lifecycle for listening to changes
 * on the Event
 */
abstract class EventViewModel<T>: ViewModel() {
    val screenEvent: LiveData<Event<T>>
        get() = _screenEvent
    private val _screenEvent = MutableLiveData<Event<T>>()

    protected fun postEvent(eventContent: T) {
        _screenEvent.postValue(Event(eventContent))
    }
}