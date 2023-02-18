package com.recc.recc_client.layout.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.recc.recc_client.models.auth.User

val BLANK_REGEX = Regex(".")

/**
 * @param T ScreenEvent that has the events compatible with this [ViewModel]
 * Base [ViewModel], it implements a [screenEvent] that has a custom lifecycle for listening to changes
 * on the Event
 */
abstract class BaseEventViewModel<T>: ViewModel() {
    private val _screenEvent = MutableLiveData<Event<T>>()
    val screenEvent: LiveData<Event<T>>
        get() = _screenEvent

    protected fun postEvent(eventContent: T) {
        _screenEvent.postValue(Event(eventContent))
    }
}