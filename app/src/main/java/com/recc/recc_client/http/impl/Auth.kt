package com.recc.recc_client.http.impl

import android.content.Context
import com.recc.recc_client.http.def.ServerRouteDefinitions
import com.recc.recc_client.layout.common.Result
import com.recc.recc_client.models.auth.*

class Auth(
    private val context: Context,
    private val http: ServerRouteDefinitions
): BaseImpl() {


    suspend fun login(email: String, password: String): Result<String> {
        val query = http.postToken(
            CreateToken(
                email = email,
                password = password,
                // TODO: Use real device info
                deviceName = "android1"
            )
        )
        return handleQuery(query) { it.token }
    }

    suspend fun register(username: String, email: String, password: String): Result<User> {
        val query = http.postUser(
            CreateUser(
                name = username,
                password = password,
                email = email
            )
        )
        return handleQuery(query) { it }
    }

    suspend fun logout(): Result<SimpleResponse> {
        val query = http.deleteToken()
        return handleQuery(query) { it }
    }

    suspend fun me(token: String): Result<User> {
        val query = http.getUserMe(formatToken(token))
        return handleQuery(query) { it }
    }
}