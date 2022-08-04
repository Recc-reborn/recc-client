package com.recc.recc_client.layout.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.recc.recc_client.utils.L
import kotlinx.coroutines.launch

class RegisterViewModel: ViewModel() {
    private val _registerBtnLD = MutableLiveData<Boolean>()
    val registerBtnLD: LiveData<Boolean> = _registerBtnLD

    init {
        L.alert("RegisterViewModel initialized...")
    }

    fun onRegisterBtn() {
        viewModelScope.launch {
            L.alert("Register Button pressed...")
            _registerBtnLD.postValue(true)
        }
    }
}