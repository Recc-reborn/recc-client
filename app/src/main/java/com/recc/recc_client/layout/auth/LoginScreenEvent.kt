package com.recc.recc_client.layout.auth

import com.recc.recc_client.layout.common.BaseScreenEvent
import com.recc.recc_client.models.auth.ErrorResponse

sealed class LoginScreenEvent: BaseScreenEvent() {
    object BtnLoginPressed: LoginScreenEvent()
    object TvRegisterInsteadPressed: LoginScreenEvent()
    data class LoginSuccessful(val token: String): LoginScreenEvent()
    data class LoginFailed(val errorResponse: ErrorResponse): LoginScreenEvent()
    object FetchMeDataFailed: LoginScreenEvent()
}