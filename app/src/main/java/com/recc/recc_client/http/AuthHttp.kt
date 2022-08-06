package com.recc.recc_client.http

import androidx.lifecycle.ViewModel
import com.recc.recc_client.layout.common.Result
import com.recc.recc_client.models.responses.ROLE_USER
import com.recc.recc_client.models.responses.Token
import com.recc.recc_client.models.responses.User
import com.recc.recc_client.models.responses.UserPost
import com.recc.recc_client.utils.Alert

class AuthHttp(private val httpApi: ServerRouteDefinitions): ViewModel() {
    inner class AuthHttpEvents {
        fun <T> onSuccess(callback: (T) -> Unit) {

        }
    }

    suspend fun login(email: String, password: String): Result<Nothing, String> {
        val query = httpApi.postToken(
            Token(
            email = email,
            password = password,
            // TODO: Use real device info
            deviceName = "android1"
        )
        )
        // TODO: Fix client crashing when server returns null as a response
        query.body()?.let {
            // TODO: Store token in shared preferences
            return Result.Success(success = it)
        } ?: run {
            return Result.Failure()
        }
    }

    suspend fun register(username: String, email: String, password: String): Result<Nothing, User?> {
        val query = httpApi.postUser(
            UserPost(
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

    fun logout() {

    }
}