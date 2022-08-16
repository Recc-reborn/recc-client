package com.recc.recc_client.models.auth

const val ERRORS_FIELD = "errors"
const val MESSAGE_FIEL = "message"
const val EMAIL_FIELD = "email"
const val PASSWORD_FIELD = "password"

data class Errors(
    val email: List<String> = listOf(),
    val password: List<String> = listOf()
)

data class ErrorResponse(
    val message: String = "",
    val errors: Errors = Errors()
)