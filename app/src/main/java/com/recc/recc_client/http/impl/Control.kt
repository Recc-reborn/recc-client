package com.recc.recc_client.http.impl

import android.content.Context
import com.recc.recc_client.R
import com.recc.recc_client.http.def.ServerRouteDefinitions
import com.recc.recc_client.layout.common.Result
import com.recc.recc_client.models.auth.ErrorResponse
import com.recc.recc_client.models.control.Artist
import com.recc.recc_client.models.control.Track
import com.recc.recc_client.utils.isOkCode

const val DEFAULT_CURRENT_PAGE = 1
const val DEFAULT_ARTISTS_PER_PAGE = 50

class Control(
    private val context: Context,
    private val http: ServerRouteDefinitions
): BaseImpl() {


    suspend fun addPreferredArtists(token: String, artists: List<Int>): Result<ErrorResponse, String> {
        val query = http.addPreferredArtists(formatToken(token), artists)
        if (query.isSuccessful) {
            if (query.code().isOkCode())
                return Result.Success(success = "Artist selection successfully done")
            return Result.Failure(failure = ErrorResponse("Artist selection failed"))
        }
        return Result.Failure(failure = ErrorResponse(context.getString(R.string.failed_query_msg)))
    }

    suspend fun fetchArtists(
        page: Int = DEFAULT_CURRENT_PAGE,
        perPage: Int = DEFAULT_ARTISTS_PER_PAGE,
        search: String? = null): Result<ErrorResponse, List<Artist>> {
        val query = http.fetchTopArtists(perPage, page, search)
        if (query.code().isOkCode()) {
            query.body()?.let {
                return Result.Success(success = it.data)
            }
            return Result.Failure(failure = ErrorResponse("Artist selection failed"))
        }
        return Result.Failure(failure = ErrorResponse(context.getString(R.string.failed_query_msg)))
    }

    suspend fun addPreferredTracks(token: String, tracks: List<Int>): Result<ErrorResponse, String> {
        val query = http.addPreferredTracks(formatToken(token), tracks)
        if (query.isSuccessful) {
            if (query.code().isOkCode())
                return Result.Success(success = "Preferred track selection successfully done")
            return Result.Failure(failure = ErrorResponse("Track selection failed"))
        }
        return Result.Failure(failure = ErrorResponse(context.getString(R.string.failed_query_msg)))
    }

    suspend fun fetchTracks(
        page: Int = DEFAULT_CURRENT_PAGE,
        perPage: Int = DEFAULT_ARTISTS_PER_PAGE,
        search: String? = null)
            : Result<ErrorResponse, List<Track>> {
        val query = http.fetchTopTracks(perPage, page, search)
        if (query.code().isOkCode()) {
            query.body()?.let {
                return Result.Success(success = it.data)
            }
            return Result.Failure(failure = ErrorResponse("Track selection failed"))
        }
        return Result.Failure(failure = ErrorResponse(context.getString(R.string.failed_query_msg)))
    }
}