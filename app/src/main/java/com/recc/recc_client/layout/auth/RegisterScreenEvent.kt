package com.recc.recc_client.layout.auth

import com.recc.recc_client.models.responses.User

sealed class RegisterScreenEvent {
    object BtnRegisterPressed: RegisterScreenEvent()
    object TvLoginInsteadPressed: RegisterScreenEvent()
    object EmailAlreadyInUseCase: RegisterScreenEvent()
    data class RegisterSuccessful(val user: User, val password: String): RegisterScreenEvent()
    data class LoginSuccessful(val user: User, val token: String): RegisterScreenEvent()
    object RegisterFailed: RegisterScreenEvent()
}