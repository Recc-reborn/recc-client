package com.recc.recc_client.http.impl

import android.content.Context
import com.recc.recc_client.R
import com.recc.recc_client.http.def.ServerRouteDefinitions
import com.recc.recc_client.layout.common.Result
import com.recc.recc_client.models.auth.*
import com.recc.recc_client.utils.Alert
import com.recc.recc_client.utils.isOkCode
import com.recc.recc_client.utils.toStringList
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Auth(private val context: Context, private val http: ServerRouteDefinitions) {

    private fun getJsonErrorResponse(body: ResponseBody): ErrorResponse {
        val json = JSONObject(body.string())
        val message = json.getString(MESSAGE_FIELD)
        Alert("message: $message")
        var emailList = listOf<String>()
        var passwordList = listOf<String>()
        if (json.has(ERRORS_FIELD)) {
            val errors = json.getJSONObject(ERRORS_FIELD)
            if (errors.has(EMAIL_FIELD)) {
                emailList = errors.getJSONArray(EMAIL_FIELD).toStringList()
            }
            if (errors.has(PASSWORD_FIELD)) {
                passwordList = errors.getJSONArray(PASSWORD_FIELD).toStringList()
            }
        }
        return ErrorResponse(message, Errors(emailList, passwordList))
    }

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

    suspend fun me(token: String?): Result<ErrorResponse, User> {
        val query = http.getUserMe("Bearer $token")
        Alert("me function")
        lateinit var result: Result<ErrorResponse, User>
        query.enqueue(object: Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                response.body()?.let {
                    result = Result.Success(success = it)
                } ?: run {
                    response.errorBody()?.let {
                        result = Result.Failure(failure = getJsonErrorResponse(it))
                    }
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Alert("t: $t")
                result = Result.Failure(failure = ErrorResponse(context.getString(R.string.failed_query_msg)))
                Alert("calling again...")
                query.clone()
            }
        })
        Alert("result: $result")
        return result
    }
}