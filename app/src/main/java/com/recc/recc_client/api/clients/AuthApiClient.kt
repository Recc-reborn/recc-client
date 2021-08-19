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

open class AuthApiClient(context: Context) : BaseApiClient(context) {
    companion object {
        private var instance: AuthApiClient? = null
        fun getInstance(context: Context): AuthApiClient = instance.also { println("VIEJO") }
            ?: synchronized(this) {
                instance ?: AuthApiClient(context).also {
                    println("NUEVO")
                    instance = it
                }
            }

    }
    private val client = HttpClient(Android) {
        expectSuccess = false
        engine {
            connectTimeout = 10_000
        }
        install(JsonFeature) {
            serializer = GsonSerializer() {
                setPrettyPrinting()
                disableHtmlEscaping()
            }
        }
        install(Auth) {
            bearer {
                //loadTokens {  BearerTokens(accessToken = token, refreshToken = token) }
            }
        }
    }
    suspend fun getToken(email: String, password: String) {
        val deviceName = Build.MODEL
        val authData = UserLogin(email, password, deviceName)
        // fetch token
        val response = request("POST", "auth/token", false, authData)
        // TODO: store token
        // store(response.data.token)
    }

    suspend fun login(email: String, password: String) {
        getToken(email, password)
        fetchUser()
    }

    suspend fun register(name: String, email: String, password: String) {
        // POST users
        val registerData = User(name, email, password)
        request("POST", "users", false, registerData)
    }

    suspend fun fetchUser() {
        // GET users/me
        val response = request("GET", "users/me", true)
        // TODO: store user data
        // store(response.data.user)
    }

    suspend fun logout() {
        // DELETE auth/token
        request("DELETE", "auth", true)
    }
}