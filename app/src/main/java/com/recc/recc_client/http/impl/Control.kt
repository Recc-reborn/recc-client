package com.recc.recc_client.http.impl

import android.content.Context
import com.recc.recc_client.R
import com.recc.recc_client.http.def.ServerRouteDefinitions
import com.recc.recc_client.layout.common.Result
import com.recc.recc_client.models.auth.ErrorResponse
import com.recc.recc_client.utils.isOkCode

class Control(
    private val context: Context,
    private val http: ServerRouteDefinitions
): BaseImpl() {

    suspend fun addPreferredArtists(token: String, artists: List<String>): Result<ErrorResponse, String> {
        val query = http.addPreferredArtists(token, artists)

        if (query.isSuccessful) {
            if (query.code().isOkCode()) {
                return Result.Success(success = "Artist selection successfully done")
            }
            return Result.Failure(failure = ErrorResponse("Artist selection failed"))
        }

        return Result.Failure(failure = ErrorResponse(context.getString(R.string.failed_query_msg)))
    }
}