package com.recc.recc_client.layout.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.recc.recc_client.utils.L
import kotlinx.coroutines.launch

/**
 * Login ViewModel that acts as a "two-way controller" in a MVC architecture, here we code the login's
 * logic and communicate with LoginFragment for navigation and other UI related stuff
 */
class LoginViewModel: ViewModel() {
    private val _loginBtnLD = MutableLiveData<Boolean>()
    val loginBtnLD: LiveData<Boolean> = _loginBtnLD

    private val _registerBtnLD = MutableLiveData<Boolean>()
    val registerBtnLD: LiveData<Boolean> = _registerBtnLD

    init {
        L.alert("LoginViewModel initialized...")
    }

    fun onLoginBtn() {
        viewModelScope.launch {
            L.alert("Login Button pressed...")
            _loginBtnLD.postValue(_loginBtnLD.value?.not())
            // TODO: Calls retrofit API for login
        }
    }

    fun onRegisterBtn() {
        viewModelScope.launch {
            L.alert("Register Button pressed...")
            _registerBtnLD.postValue(_registerBtnLD.value?.not())
        }
    }
}