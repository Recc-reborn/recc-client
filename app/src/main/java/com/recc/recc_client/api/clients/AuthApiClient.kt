package com.recc.recc_client.api.clients;

import android.os.Build
import com.recc.recc_client.api.schema.UserLogin
import com.recc.recc_client.api.schema.User

open class AuthApiClient : BaseApiClient() {
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