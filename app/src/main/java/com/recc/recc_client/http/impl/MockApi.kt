package com.recc.recc_client.http.impl

import android.content.Context
import com.recc.recc_client.R
import com.recc.recc_client.http.def.MockApiRouteDefinitions
import com.recc.recc_client.layout.common.Result
import com.recc.recc_client.models.auth.LastFmErrorResponse
import com.recc.recc_client.models.control.Playlist
import com.recc.recc_client.utils.isOkCode

class MockApi(
    private val context: Context,
    private val mockApi: MockApiRouteDefinitions
): BaseImpl() {

    suspend fun getPlaylists(): Result<LastFmErrorResponse, List<Playlist>> {
        val query = mockApi.getPlaylists()
        query.body().let {
            if (query.code().isOkCode()) {
                return Result.Success(success = it.orEmpty())
            }
        }
        return Result.Failure(failure = LastFmErrorResponse(context.getString(R.string.failed_query_msg)))
    }
}