package com.recc.recc_client.layout.auth

import com.recc.recc_client.layout.common.BaseScreenEvent
import com.recc.recc_client.models.auth.ErrorResponse
import com.recc.recc_client.models.auth.User

sealed class RegisterScreenEvent: BaseScreenEvent() {
    object BtnRegisterPressed: RegisterScreenEvent()
    object TvLoginInsteadPressed: RegisterScreenEvent()
    object EmailAlreadyInUseCase: RegisterScreenEvent()
    data class RegisterSuccessful(val user: User, val password: String): RegisterScreenEvent()
    data class LoginSuccessful(val user: User, val token: String): RegisterScreenEvent()
    data class RegisterFailed(val errorResponse: ErrorResponse): RegisterScreenEvent()
}