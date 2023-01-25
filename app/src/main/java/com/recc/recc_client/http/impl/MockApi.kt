package com.recc.recc_client.http.impl

import android.content.Context
import com.recc.recc_client.http.def.MockApiRouteDefinitions
import com.recc.recc_client.layout.common.Result
import com.recc.recc_client.models.control.Playlist
import com.recc.recc_client.models.control.Track

class MockApi(
    private val context: Context,
    private val mockApi: MockApiRouteDefinitions
): BaseImpl() {

    suspend fun getPlaylists(): Result<List<Playlist>> {
        val query = mockApi.getPlaylists()
        return handleQuery(query) { it }
    }

    suspend fun getTracks(): Result<List<Track>> {
        val query = mockApi.getTracks()
        return handleQuery(query) { it }
    }
}