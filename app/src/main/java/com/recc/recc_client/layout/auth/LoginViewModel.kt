package com.recc.recc_client.layout.auth

import androidx.lifecycle.viewModelScope
import com.recc.recc_client.http.AuthHttp
import com.recc.recc_client.layout.common.EventViewModel
import com.recc.recc_client.layout.common.onFailure
import com.recc.recc_client.layout.common.onSuccess
import com.recc.recc_client.models.auth.ErrorResponse
import com.recc.recc_client.utils.Alert
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Login ViewModel that acts as a "two-way controller" in a MVC architecture, here we code the login's
 * logic and communicate with LoginFragment for navigation and other UI related stuff
 */
class LoginViewModel(private val http: AuthHttp): EventViewModel<LoginScreenEvent>() {
    var emailRegex: Regex? = null
    var passwordRegex: Regex? = null

    fun login(email: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val exList = listOf(emailRegex, passwordRegex)
            exList.all { it != null }.also {
                if (email.matches(emailRegex ?: ".".toRegex()) && password.matches(passwordRegex ?: ".".toRegex())) {
                    http.login(email, password)
                        .onSuccess {
                            Alert("token: $it")
                            postEvent(LoginScreenEvent.LoginSuccessful(it))
                        }.onFailure {
                            Alert("msg: $it")
                             postEvent(LoginScreenEvent.LoginFailed(it ?: ErrorResponse()))
                        }
                }
            }
        }
    }


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