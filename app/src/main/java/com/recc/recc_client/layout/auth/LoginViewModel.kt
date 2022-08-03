package com.recc.recc_client.layout.auth

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.recc.recc_client.utils.L

class LoginViewModel: ViewModel() {
    private val _loginBtnLD = MutableLiveData<Boolean>()
    val loginBtnLD: LiveData<Boolean> = _loginBtnLD

    init {
        L.alert("Login Button pressed...")
        _loginBtnLD.postValue(false)
    }

    fun onLoginBtnPressed(view: View) {
        L.alert("Login Button pressed...")
        _loginBtnLD.postValue(true)
    }
}