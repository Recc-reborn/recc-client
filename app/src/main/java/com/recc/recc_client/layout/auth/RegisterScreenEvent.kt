package com.recc.recc_client.layout.auth

sealed class RegisterScreenEvent {
    object BtnRegisterPressed: RegisterScreenEvent()
    object TvLoginInsteadPressed: RegisterScreenEvent()
    object EmailAlreadyInUseCase: RegisterScreenEvent()
}