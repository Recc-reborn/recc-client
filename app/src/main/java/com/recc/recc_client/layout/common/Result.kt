package com.recc.recc_client.layout.common

import com.recc.recc_client.models.auth.ErrorResponse

sealed class Result<S>(private val failure: ErrorResponse? = null,
                   private val success: S? = null) {

    data class Success<S>(val success: S?): Result<S>(success = success)
    data class Failure<S>(val failure: ErrorResponse): Result<S>(failure = failure)
}

fun <S> Result<S>.onSuccess (callback: (S) -> Unit): Result<S> {
    if (this is Result.Success) {
        success?.let {
            callback(it)
        }
    }
    return this
}

fun <S> Result<S>.onFailure (callback: (ErrorResponse) -> Unit): Result<S> {
    if (this is Result.Failure) {
        callback(failure)
    }
    return this
}
