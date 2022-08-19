package com.recc.recc_client.http

import android.content.Context
import androidx.lifecycle.ViewModel
import com.recc.recc_client.R
import com.recc.recc_client.layout.common.Result
import com.recc.recc_client.utils.isOkCode

class LastFmHttp(private val context: Context, private val httpApi: LastFmRouteDefinitions): ViewModel() {
    suspend fun getToken(): Result<String, String> {
        val query = httpApi.baseRequest(LAST_FM_GET_TOKEN_METHOD, context.getString(R.string.last_fm_token))
        if (query.code().isOkCode()) {
            query.body()?.let {
                return Result.Success(success = it.token)
            }
        }
        return Result.Failure(failure = context.getString(R.string.last_fm_api_error_msg))
    }
}