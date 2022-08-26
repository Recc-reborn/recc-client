package com.recc.recc_client.models.auth

const val ERRORS_FIELD = "errors"
const val MESSAGE_FIELD = "message"
const val EMAIL_FIELD = "email"
const val PASSWORD_FIELD = "password"
const val ERROR_FIELD_NAME = "error"
const val MESSAGE_FIELD_NAME = "message"

data class Errors(
    val email: List<String> = listOf(),
    val password: List<String> = listOf()
)

data class ErrorResponse(
    val message: String = "",
    val errors: Errors = Errors()
)

data class LastFmErrorResponse(
    val message: String = "",
    val error: String = ""
)