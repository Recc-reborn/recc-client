package com.recc.recc_client.layout.auth

import androidx.lifecycle.viewModelScope
import com.recc.recc_client.http.AuthHttp
import com.recc.recc_client.layout.common.EventViewModel
import com.recc.recc_client.layout.common.onFailure
import com.recc.recc_client.layout.common.onSuccess
import com.recc.recc_client.utils.Alert
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel(private val httpApi: AuthHttp): EventViewModel<RegisterScreenEvent>() {
    var usernameRegex: Regex? = null
    var emailRegex: Regex? = null
    var passwordRegex: Regex? = null

    fun register(username: String, email: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val evalRegexList = listOf(usernameRegex, emailRegex, passwordRegex)
            evalRegexList.all { it != null }.let {
                val usernameValid = username.matches(usernameRegex ?: ".".toRegex())
                val emailValid = email.matches(emailRegex ?: ".".toRegex())
                val passwordValid = password.matches(passwordRegex ?: ".".toRegex())
                if (usernameValid && emailValid && passwordValid) {
                    httpApi.register(username, email, password)
                        .onFailure {
                            postEvent(RegisterScreenEvent.RegisterFailed)
                        }
                        .onSuccess { user ->
                            user?.let {
                                postEvent(RegisterScreenEvent.RegisterSuccessful(it))
                            }
                        }
                }
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