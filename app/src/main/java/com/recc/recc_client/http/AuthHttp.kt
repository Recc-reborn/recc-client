package com.recc.recc_client.http

import android.content.Context
import androidx.lifecycle.ViewModel
import com.recc.recc_client.R
import com.recc.recc_client.layout.common.Result
import com.recc.recc_client.models.responses.*
import com.recc.recc_client.utils.Alert
import com.recc.recc_client.utils.isOkCode

class AuthHttp(private val context: Context, private val httpApi: ServerRouteDefinitions): ViewModel() {

    suspend fun login(email: String, password: String): Result<String, String> {
        val query = httpApi.postToken(
            CreateToken(
                email = email,
                password = password,
                // TODO: Use real device info
                deviceName = "android1"
            )
        )
        Alert("${query.code()}")
        Alert("${query.isSuccessful}")
        if (query.isSuccessful) {
            if (query.code().isOkCode()) {
                query.body()?.let {
                    Alert("$it")
                    return Result.Success(success = it.token)
                }
            }
        }
        query.body()?.let {
            return Result.Failure(failure = it.message)
        }
        return Result.Failure(failure = context.getString(R.string.failed_query_msg))
    }

    suspend fun register(username: String, email: String, password: String): Result<Nothing, User?> {
        val query = httpApi.postUser(
            CreateUser(
                name = username,
                password = password,
                email = email,
                role = ROLE_USER
            )
        )

        query.body()?.let { user ->
            return Result.Success(success = user)
        } ?: run {
            return Result.Failure(null)
        }
    }

    suspend fun logout(): Result<String, String> {
        val query = httpApi.deleteToken()
        query.body()?.let { res ->
            return Result.Success(success = res.message)
        } ?: run {
            return Result.Failure(null)
        }
    }
}