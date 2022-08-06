package com.recc.recc_client.layout.auth

import com.recc.recc_client.models.responses.User

sealed class RegisterScreenEvent {
    object BtnRegisterPressed: RegisterScreenEvent()
    object TvLoginInsteadPressed: RegisterScreenEvent()
    object EmailAlreadyInUseCase: RegisterScreenEvent()
    data class RegisterSuccessful(val user: User): RegisterScreenEvent()
    object RegisterFailed: RegisterScreenEvent()
}