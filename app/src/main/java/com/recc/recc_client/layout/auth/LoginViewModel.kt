package com.recc.recc_client.layout.auth

import androidx.lifecycle.viewModelScope
import com.recc.recc_client.http.ServerRouteDefinitions
import com.recc.recc_client.layout.common.EventViewModel
import com.recc.recc_client.models.responses.Token
import com.recc.recc_client.utils.Alert
import com.recc.recc_client.utils.isOkCode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Login ViewModel that acts as a "two-way controller" in a MVC architecture, here we code the login's
 * logic and communicate with LoginFragment for navigation and other UI related stuff
 */
class LoginViewModel(private val httpApi: ServerRouteDefinitions): EventViewModel<LoginScreenEvent>() {
    var emailRegex: kotlin.text.Regex? = null
    var passwordRegex: kotlin.text.Regex? = null

    fun login(email: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val exList = listOf(emailRegex, passwordRegex)
            exList.all { it != null }.also {
                if (email.matches(emailRegex ?: ".".toRegex()) && password.matches(passwordRegex ?: ".".toRegex())) {
                    val query = httpApi.postToken(Token(
                        email = email,
                        password = password,
                        // TODO: Use real device info
                        deviceName = "android1"
                    ))
                    // TODO: Fix client crashing when server returns null as a response
                    query.body()?.let {
                        // TODO: Store token in shared preferences
                        Alert("body: $it")
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