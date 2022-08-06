package com.recc.recc_client.layout.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class EventViewModel<T>: ViewModel() {
    val screenEvent: LiveData<Event<T>>
        get() = _screenEvent
    private val _screenEvent = MutableLiveData<Event<T>>()

    protected fun postEvent(eventContent: T) {
        _screenEvent.postValue(Event(eventContent))
    }
}