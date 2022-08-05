package com.recc.recc_client.layout.auth

import androidx.lifecycle.viewModelScope
import com.recc.recc_client.http.ServerRoutesDefinitions
import com.recc.recc_client.layout.common.EventViewModel
import kotlinx.coroutines.launch

/**
 * Login ViewModel that acts as a "two-way controller" in a MVC architecture, here we code the login's
 * logic and communicate with LoginFragment for navigation and other UI related stuff
 */
class LoginViewModel(val httpApi: ServerRoutesDefinitions): EventViewModel<LoginScreenEvent>() {

    fun onBtnLogin() {
        viewModelScope.launch {

            postEvent(LoginScreenEvent.BtnLoginPressed)
        }
    }

    fun onTvRegisterInstead() {
        viewModelScope.launch {
            postEvent(LoginScreenEvent.TvRegisterInsteadPressed)
        }
    }
}