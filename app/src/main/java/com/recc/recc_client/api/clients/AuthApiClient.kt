package com.recc.recc_client.api.clients;

import android.content.Context
import android.os.Build
import com.recc.recc_client.api.schema.UserLogin
import com.recc.recc_client.api.schema.User
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.auth.*
import io.ktor.client.features.auth.providers.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import kotlinx.serialization.json.Json

open class AuthApiClient(context: Context) : BaseApiClient(context) {
    private val client = HttpClient(Android) {
        expectSuccess = false
        engine {
            connectTimeout = 10_000
        }
        install(JsonFeature) {
            serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                prettyPrint = true
                ignoreUnknownKeys = true
            })
        }
        install(Auth) {
            bearer {
                //loadTokens {  BearerTokens(accessToken = token, refreshToken = token) }
            }
        }
    }
    public suspend fun getToken(email: String, password: String) {
        val deviceName = Build.MODEL
        val authData = UserLogin(email, password, deviceName)
        // fetch token
        val response = request("POST", "auth/token", false, authData)
        // TODO: store token
        // store(response.data.token)
    }

    public suspend fun login(email: String, password: String) {
        getToken(email, password)
        fetchUser()
    }

    public suspend fun register(name: String, email: String, password: String) {
        // POST users
        val registerData = User(name, email, password)
        request("POST", "users", false, registerData)
    }

    public suspend fun fetchUser() {
        // GET users/me
        val response = request("GET", "users/me", true)
        // TODO: store user data
        // store(response.data.user)
    }

    public suspend fun logout() {
        // DELETE auth/token
        request("DELETE", "auth", true)
    }
}