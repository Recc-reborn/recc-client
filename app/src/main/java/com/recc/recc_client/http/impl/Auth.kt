package com.recc.recc_client.http.impl

import android.content.Context
import com.recc.recc_client.R
import com.recc.recc_client.http.def.ServerRouteDefinitions
import com.recc.recc_client.layout.common.Result
import com.recc.recc_client.models.auth.*
import com.recc.recc_client.utils.isOkCode

class Auth(
    private val context: Context,
    private val http: ServerRouteDefinitions
): BaseImpl() {

    suspend fun login(email: String, password: String): Result<ErrorResponse, String> {
        val query = http.postToken(
            CreateToken(
                email = email,
                password = password,
                // TODO: Use real device info
                deviceName = "android1"
            )
        )
        query.body()?.let {
            if (query.code().isOkCode()) {
                return Result.Success(success = it.token)
            }
        }
        query.errorBody()?.apply {
            return Result.Failure(failure = getJsonErrorResponse(this))
        }
        return Result.Failure(failure = ErrorResponse(context.getString(R.string.failed_query_msg)))
    }

    suspend fun register(username: String, email: String, password: String): Result<ErrorResponse, User?> {
        val query = http.postUser(
            CreateUser(
                name = username,
                password = password,
                email = email
            )
        )
        query.body()?.let {
            if (query.code().isOkCode()) {
                return Result.Success(success = it)
            }
        }
        query.errorBody()?.apply {
            return Result.Failure(failure = getJsonErrorResponse(this))
        }
        return Result.Failure(failure = ErrorResponse(context.getString(R.string.failed_query_msg)))
    }

    suspend fun logout(): Result<ErrorResponse, SimpleResponse> {
        val query = http.deleteToken()
        query.body()?.let {
            if (query.code().isOkCode()) {
                return Result.Success(success = it)
            }
        }
        query.errorBody()?.let {
            return Result.Failure(failure = getJsonErrorResponse(it))
        }
        return Result.Failure(failure = ErrorResponse(context.getString(R.string.failed_query_msg)))
    }

    suspend fun me(token: String): Result<ErrorResponse, User> {
        val query = http.getUserMe(formatToken(token))
        query.body()?.let {
            if (query.code().isOkCode()) {
                return Result.Success(success = it)
            }
        }
        query.errorBody()?.let {
            return Result.Failure(failure = getJsonErrorResponse(it))
        }
        return Result.Failure(failure = ErrorResponse(context.getString(R.string.failed_query_msg)))
    }
}