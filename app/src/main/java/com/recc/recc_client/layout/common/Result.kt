package com.recc.recc_client.layout.common

sealed class Result<F, S>(private val failure: F? = null,
                   private val success: S? = null) {

    data class Success<F, S>(val success: S): Result<F, S>(success = success)
    data class Failure<F, S>(val failure: F): Result<F, S>(failure = failure)
}

fun <F, S> Result<F, S>.onSuccess (callback: (S) -> Unit): Result<F, S> {
    if (this is Result.Success) {
        success?.let {
            callback(it)
        }
    }
    return this
}

fun <F, S> Result<F, S>.onFailure (callback: (F) -> Unit): Result<F, S> {
    if (this is Result.Failure) {
        callback(failure)
    }
    return this
}
