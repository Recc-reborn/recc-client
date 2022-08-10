package com.recc.recc_client.http

import android.content.Context
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.recc.recc_client.R
import com.recc.recc_client.layout.common.Result
import com.recc.recc_client.models.auth.*
import com.recc.recc_client.utils.Alert
import com.recc.recc_client.utils.isOkCode
import org.json.JSONObject
import java.lang.reflect.Type

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
        if (query.code().isOkCode()) {
            query.body()?.let {
                return Result.Success(success = it.token)
            }
        }
        query.errorBody()?.apply {
            val json = JSONObject(this.string())
            val errors = json.getJSONObject("errors")
            val errorList = errors.getJSONArray("email")
            val msg = if (errorList.length() > 0) {
                errorList[0].toString()
            } else {
                json.getString("message")
            }
            return Result.Failure(failure = msg)
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
        query.body()?.let {
            TODO()
        } ?: run {
            return Result.Failure(null)
        }
    }
}