package com.recc.recc_client.http.impl

import android.content.Context
import com.recc.recc_client.R
import com.recc.recc_client.http.def.ServerRouteDefinitions
import com.recc.recc_client.layout.common.Result
import com.recc.recc_client.models.auth.ErrorResponse
import com.recc.recc_client.models.control.TopArtists
import com.recc.recc_client.utils.isOkCode

const val DEFAULT_CURRENT_PAGE = 1
const val DEFAULT_ARTISTS_PER_PAGE = 50

class Control(
    private val context: Context,
    private val http: ServerRouteDefinitions
): BaseImpl() {

    suspend fun addPreferredArtists(token: String, artists: List<Int>): Result<ErrorResponse, String> {
        val query = http.addPreferredArtists("Bearer $token", artists)
        if (query.isSuccessful) {
            if (query.code().isOkCode()) {
                return Result.Success(success = "Artist selection successfully done")
            }
            return Result.Failure(failure = ErrorResponse("Artist selection failed"))
        }
        return Result.Failure(failure = ErrorResponse(context.getString(R.string.failed_query_msg)))
    }

    suspend fun getArtists(
        page: Int = DEFAULT_CURRENT_PAGE,
        perPage: Int = DEFAULT_ARTISTS_PER_PAGE,
        search: String? = null)
            : Result<ErrorResponse, List<TopArtists>> {
        val query = http.getTopArtists(perPage, page, search)
        if (query.code().isOkCode()) {
            query.body()?.let {
                return Result.Success(success = it.data)
            }
            return Result.Failure(failure = ErrorResponse("Artist selection failed"))
        }
        return Result.Failure(failure = ErrorResponse(context.getString(R.string.failed_query_msg)))
    }
}