package com.recc.recc_client.http.clients;

import android.content.Context
import android.os.Build
import android.widget.Toast
import com.google.gson.Gson
import com.recc.recc_client.R
import com.recc.recc_client.http.schema.UserLogin
import com.recc.recc_client.http.schema.User
import com.recc.recc_client.http.schema.UserInfo
import io.ktor.client.call.*
import kotlinx.coroutines.*
import java.io.File

open class AuthApiClient(context: Context) {
    private val instance = BaseApiClient.getInstance(context)

    fun token(): String? = instance.token

    fun userInfo(): UserInfo? = instance.userInfo

    suspend fun login(context: Context, email: String, password: String) {
        when (getToken(context, email, password)) {
            context.getString(R.string.server_ok_code) -> fetchUser(context)
            context.getString(R.string.login_data_required_code) -> CoroutineScope(Dispatchers.Main).launch {
                Toast.makeText(context, "Wrong email/password", Toast.LENGTH_LONG).show()
            }
        }
        val gson = Gson()
        instance.userInfo?.also {
            val jsonString = gson.toJson(it)
            val file = File(context.filesDir, context.getString(R.string.user_info_path))
            file.writeText(jsonString)
        }
    }

    suspend fun register(name: String, email: String, password: String): Int? {
        // POST users
        val registerData = User(name, email, password)
        return instance.request("POST", "users", null, registerData)?.status?.value
    }

    private suspend fun fetchUser(context: Context) {
        // GET users/me
        println("TOKEN: " + instance.token)
        instance.request("GET", "users/me", instance.token)?.also {
            instance.userInfo = it.receive<UserInfo>()
        }
    }

    suspend fun logout(context: Context) {
        // DELETE auth/token
        instance.request("DELETE", "auth", instance.token)
        instance.token = null
        instance.userInfo = null
        val tokenFile = File(context.filesDir, context.getString(R.string.token_path))
        if (tokenFile.exists())
            tokenFile.delete()
        val infoFile = File(context.filesDir, context.getString(R.string.user_info_path))
        if (infoFile.exists())
            infoFile.delete()
    }

    private suspend fun getToken (context: Context, email: String, password: String): String {
        val deviceName = Build.MODEL
        val authData = UserLogin(email, password, deviceName)
        // fetch token
        val res = instance.request("POST", "auth/token", instance.token, authData, context)
        if (res?.status?.value == context.getString(R.string.server_ok_code).toInt()) {
            res.also {
                val token: String = res.receive()
                val file = File(context.filesDir, context.getString(R.string.token_path))
                file.writeText(token)
                instance.token = token
            }
        }
        return res?.status!!.value.toString()
    }
}