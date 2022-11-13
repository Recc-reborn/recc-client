package com.recc.recc_client.http.impl

import android.content.Context
import com.recc.recc_client.R
import com.recc.recc_client.http.def.*
import com.recc.recc_client.layout.common.Result
import com.recc.recc_client.models.auth.ERROR_FIELD_NAME
import com.recc.recc_client.models.auth.LastFmErrorResponse
import com.recc.recc_client.models.auth.MESSAGE_FIELD_NAME
import com.recc.recc_client.models.last_fm.Artists
import com.recc.recc_client.models.last_fm.Search
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

    suspend fun getTopArtists(
        page: Int? = null,
        limit: Int? = null
    ): Result<LastFmErrorResponse, Artists> {
        val query = http.getTopArtists(GET_TOP_ARTIST_METHOD, context.getString(R.string.last_fm_token), limit, page)
        query.body()?.let {
            if (query.code().isOkCode()) {
                return Result.Success(success = it)
            }
        }
        query.errorBody()?.let {
            return Result.Failure(failure = getErrorBody(it))
        }
        return Result.Failure(failure = LastFmErrorResponse(context.getString(R.string.failed_query_msg)))
    }

    suspend fun getArtistSearch(
        artist: String,
        page: Int? = null,
        limit: Int? = null
    ):Result<LastFmErrorResponse, Search> {
        val query = http.getArtistSearch(artist, GET_ARTIST_SEARCH_METHOD, context.getString(R.string.last_fm_token), limit, page)
        query.body()?.let {
            if (query.code().isOkCode()) {
                return Result.Success(success = it)
            }
        }
        query.errorBody()?.let {
            return Result.Failure(failure = getErrorBody(it))
        }
        return Result.Failure(failure = LastFmErrorResponse(context.getString(R.string.failed_query_msg)))
    }
}