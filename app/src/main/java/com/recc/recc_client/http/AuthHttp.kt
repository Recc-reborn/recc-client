package com.recc.recc_client.http

import android.content.Context
import androidx.lifecycle.ViewModel
import com.recc.recc_client.R
import com.recc.recc_client.layout.common.Result
import com.recc.recc_client.models.auth.*
import com.recc.recc_client.utils.isOkCode
import com.recc.recc_client.utils.toStringList
import okhttp3.ResponseBody
import org.json.JSONObject

class AuthHttp(private val context: Context, private val httpApi: ServerRouteDefinitions): ViewModel() {

    private fun getJsonObject(body: ResponseBody): ErrorResponse {
        val json = JSONObject(body.string())
        val message = json.getString(MESSAGE_FIELD)
        var emaiList = listOf<String>()
        var passwordList = listOf<String>()
        if (json.has(ERRORS_FIELD)) {
            val errors = json.getJSONObject(ERRORS_FIELD)
            if (errors.has(EMAIL_FIELD)) {
                emaiList = errors.getJSONArray(EMAIL_FIELD).toStringList()
            }
            if (errors.has(PASSWORD_FIELD)) {
                passwordList = errors.getJSONArray(PASSWORD_FIELD).toStringList()
            }
        }
        return ErrorResponse(message, Errors(emaiList, passwordList))
    }

    suspend fun login(email: String, password: String): Result<ErrorResponse, String> {
        val query = httpApi.postToken(
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
            return Result.Failure(failure = getJsonObject(this))
        }
        return Result.Failure(failure = ErrorResponse(context.getString(R.string.failed_query_msg)))
    }

    suspend fun register(username: String, email: String, password: String): Result<ErrorResponse, User?> {
        val query = httpApi.postUser(
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
            return Result.Failure(failure = getJsonObject(this))
        }
        return Result.Failure(failure = ErrorResponse(context.getString(R.string.failed_query_msg)))
    }

    suspend fun logout(): Result<ErrorResponse, SimpleResponse> {
        val query = httpApi.deleteToken()
        query.body()?.let {
            if (query.code().isOkCode()) {
                return Result.Success(success = it)
            }
        }
        query.errorBody()?.let {
            return Result.Failure(failure = getJsonObject(it))
        }
        return Result.Failure(failure = ErrorResponse(context.getString(R.string.failed_query_msg)))
    }
}