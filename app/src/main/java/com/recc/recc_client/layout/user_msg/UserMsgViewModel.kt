package com.recc.recc_client.layout.user_msg

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserMsgViewModel: ViewModel() {

    private val _msg = MutableLiveData<String>()
    val msg: LiveData<String>
        get() = _msg

    fun postMessage(msg: String) {
        _msg.postValue(msg)
    }
}