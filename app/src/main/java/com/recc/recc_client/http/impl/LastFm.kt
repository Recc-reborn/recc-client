package com.recc.recc_client.http.impl

import android.content.Context
import com.recc.recc_client.R
import com.recc.recc_client.http.def.LastFmRouteDefinitions

class LastFm(private val context: Context, private val http: LastFmRouteDefinitions) {

    suspend fun getTopArtists() {
        val query = http.getTopArtists(context.getString(R.string.last_fm_base_endpoint))
    }
}