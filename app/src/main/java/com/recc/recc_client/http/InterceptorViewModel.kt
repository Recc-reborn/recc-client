package com.recc.recc_client.http

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.recc.recc_client.utils.Alert

class InterceptorViewModel: ViewModel() {
    private val _connection = MutableLiveData(false)
    val connection: LiveData<Boolean>
        get() = _connection

    private val _retry = MutableLiveData(false)
    val retry: LiveData<Boolean>
        get() = _retry

    fun setIsConnected(status: Boolean) {
        _connection.postValue(status)
    }

    fun retryConnection() {
        Alert("post value")
        _retry.postValue(true)
    }
}