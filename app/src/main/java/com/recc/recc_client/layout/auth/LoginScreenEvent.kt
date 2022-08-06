package com.recc.recc_client.layout.auth

sealed class LoginScreenEvent {
    object BtnLoginPressed: LoginScreenEvent()
    object TvRegisterInsteadPressed: LoginScreenEvent()
    data class LoginSuccessful(val token: String): LoginScreenEvent()
    object LoginFailed: LoginScreenEvent()
}