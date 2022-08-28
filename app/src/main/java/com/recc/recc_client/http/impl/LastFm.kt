package com.recc.recc_client.http.impl

import android.content.Context
import com.recc.recc_client.R
import com.recc.recc_client.http.def.DEFAULT_LIMIT
import com.recc.recc_client.http.def.DEFAULT_PAGE
import com.recc.recc_client.http.def.GET_TOP_ARTIST_METHOD
import com.recc.recc_client.http.def.LastFmRouteDefinitions
import com.recc.recc_client.layout.common.Result
import com.recc.recc_client.models.auth.ERROR_FIELD_NAME
import com.recc.recc_client.models.auth.LastFmErrorResponse
import com.recc.recc_client.models.auth.MESSAGE_FIELD_NAME
import com.recc.recc_client.models.last_fm.Artists
import com.recc.recc_client.utils.Alert
import com.recc.recc_client.utils.isOkCode
import okhttp3.ResponseBody
import org.json.JSONObject

class LastFm(private val context: Context, private val http: LastFmRouteDefinitions) {
    private fun getErrorBody(res: ResponseBody): LastFmErrorResponse {
        val json = JSONObject(res.string())
        var msg = ""
        if (json.has(MESSAGE_FIELD_NAME)) {
            msg = json.getString(MESSAGE_FIELD_NAME)
        }
        var error = ""
        if (json.has(ERROR_FIELD_NAME)) {
            error = json.getString(ERROR_FIELD_NAME)
        }
        return LastFmErrorResponse(msg, error)
    }

    suspend fun getTopArtists(page: Int = DEFAULT_PAGE, limit: Int = DEFAULT_LIMIT): Result<LastFmErrorResponse, Artists> {
        val query = http.getTopArtists(GET_TOP_ARTIST_METHOD, context.getString(R.string.last_fm_token), limit, page)
        query.body()?.let {
            Alert("ok")
            if (query.code().isOkCode()) {
                return Result.Success(success = it)
            }
        }
        query.errorBody()?.let {
            Alert("not ok")
            return Result.Failure(failure = getErrorBody(it))
        }
        Alert("ay wey")
        return Result.Failure(failure = LastFmErrorResponse(context.getString(R.string.failed_query_msg)))
    }
}