package com.recc.recc_client.http.impl

import com.recc.recc_client.models.auth.*
import com.recc.recc_client.utils.Alert
import com.recc.recc_client.utils.toStringList
import okhttp3.ResponseBody
import org.json.JSONObject

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
}