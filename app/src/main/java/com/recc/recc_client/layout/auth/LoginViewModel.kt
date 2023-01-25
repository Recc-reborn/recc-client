package com.recc.recc_client.layout.auth

import androidx.lifecycle.viewModelScope
import com.recc.recc_client.http.impl.Auth
import com.recc.recc_client.layout.common.*
import com.recc.recc_client.models.auth.ErrorResponse
import com.recc.recc_client.utils.Alert
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Login ViewModel that acts as a "two-way controller" in a MVC architecture, here we code the login's
 * logic and communicate with LoginFragment for navigation and other UI related stuff
 */
class LoginViewModel(
    private val http: Auth,
    private val meData: MeDataViewModel
): BaseEventViewModel<LoginScreenEvent>() {
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
                                postEvent(LoginScreenEvent.LoginSuccessful(it))
                            }.onFailure {
                                postEvent(LoginScreenEvent.LoginFailed(it))
                            }
                    }
                }
            }
        }
    }

    fun getMeData(token: String?) {
        viewModelScope.launch {
            CoroutineScope(Dispatchers.IO).launch {
                http.me(token.orEmpty())
                    .onSuccess {
                        Alert("getMeData success")
                        meData.postUser(it)
                    }
                    .onFailure {
                        Alert("getMeData error: $it")
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