package com.recc.recc_client.layout.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.recc.recc_client.models.auth.User

class MeDataViewModel: ViewModel() {
    private val _meData = MutableLiveData<User?>()
    val meData: LiveData<User?>
        get() = _meData

    fun postUser(user: User) {
        _meData.postValue(user)
    }

    fun clear() {
        _meData.postValue(null)
    }
}