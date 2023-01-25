package com.recc.recc_client.layout.auth

import androidx.lifecycle.viewModelScope
import com.recc.recc_client.http.impl.Auth
import com.recc.recc_client.layout.common.BLANK_REGEX
import com.recc.recc_client.layout.common.BaseEventViewModel
import com.recc.recc_client.layout.common.onFailure
import com.recc.recc_client.layout.common.onSuccess
import com.recc.recc_client.models.auth.ErrorResponse
import com.recc.recc_client.models.auth.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel(private val http: Auth): BaseEventViewModel<RegisterScreenEvent>() {
    var usernameRegex: Regex? = null
    var emailRegex: Regex? = null
    var passwordRegex: Regex? = null

    fun register(username: String, email: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val evalRegexList = listOf(usernameRegex, emailRegex, passwordRegex)
            evalRegexList.all { it != null }.let {
                val usernameValid = username.matches(usernameRegex ?: BLANK_REGEX)
                val emailValid = email.matches(emailRegex ?: BLANK_REGEX)
                val passwordValid = password.matches(passwordRegex ?: BLANK_REGEX)
                if (usernameValid && emailValid && passwordValid) {
                    http.register(username, email, password)
                        .onSuccess { user ->
                            user?.let {
                                postEvent(RegisterScreenEvent.RegisterSuccessful(it, password))
                            }
                        }
                        .onFailure {
                            postEvent(RegisterScreenEvent.RegisterFailed(it))
                        }
                }
            }
        }
    }

    fun login(user: User, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            http.login(user.email, password).onSuccess { token ->
                postEvent(RegisterScreenEvent.LoginSuccessful(user, token))
            }
        }
    }

    fun onBtnRegister() {
        viewModelScope.launch {
            // TODO: Creates user
            postEvent(RegisterScreenEvent.BtnRegisterPressed)
        }
    }

    fun onTvLoginInstead() {
        viewModelScope.launch {
            postEvent(RegisterScreenEvent.TvLoginInsteadPressed)
        }
    }
}