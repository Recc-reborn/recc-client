package com.recc.recc_client.http.impl

import com.recc.recc_client.layout.common.Result
import com.recc.recc_client.models.auth.*
import com.recc.recc_client.utils.Alert
import com.recc.recc_client.utils.isOkCode
import com.recc.recc_client.utils.toStringList
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Response

open class BaseImpl {

    protected fun formatToken(token: String) = "Bearer $token"
    fun getJsonErrorResponse(body: ResponseBody): ErrorResponse {
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

    protected fun <S, T>handleQuery(query: Response<T>, success: (T) -> S): Result<S> {
        if (query.isSuccessful) {
            query.body()?.let {
                return Result.Success(success = success(it))
            }
        }
        query.errorBody()?.apply {
            return Result.Failure(failure = getJsonErrorResponse(this))
        }
        return Result.Failure(failure = ErrorResponse("Error connecting to server"))
    }

    protected fun handleNoBodyQuery(query: Response<Void>): Result<String> {
        if (query.isSuccessful) {
            if (query.code().isOkCode()) {
                return Result.Success(success = "Query successful")
            }
        }
        query.errorBody()?.apply {
            return Result.Failure(failure = getJsonErrorResponse(this))
        }
        return Result.Failure(failure = ErrorResponse("Error connecting to server"))
    }
}