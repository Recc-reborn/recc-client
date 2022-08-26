package com.recc.recc_client.layout.auth

import androidx.lifecycle.viewModelScope
import com.recc.recc_client.http.impl.Auth
import com.recc.recc_client.layout.common.BLANK_REGEX
import com.recc.recc_client.layout.common.BaseEventViewModel
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
class LoginViewModel(private val http: Auth): BaseEventViewModel<LoginScreenEvent>() {
    var emailRegex: Regex? = null
    var passwordRegex: Regex? = null

    fun login(email: String, password: String) {
        viewModelScope.launch {
            CoroutineScope(Dispatchers.IO).launch {
                val exList = listOf(emailRegex, passwordRegex)
                exList.all { it != null }.also {
                    if (email.matches(emailRegex ?: BLANK_REGEX)
                        && password.matches(passwordRegex ?: BLANK_REGEX)
                    ) {
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
    }

    fun getMeData() {
        viewModelScope.launch {
            CoroutineScope(Dispatchers.IO).launch {
                http.me()
                    .onSuccess {
                        _meData.postValue(it)
                    }
                    .onFailure {
                        postEvent(LoginScreenEvent.FetchMeDataFailed)
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