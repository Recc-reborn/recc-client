package com.recc.recc_client.api.clients

import android.content.Context

class PlaybackApiClient(context: Context) : BaseApiClient(context) {
    private val instance: AuthApiClient? = AuthApiClient.getInstance(context)

    fun sendPlayback() {
        // POST playbacks
    }
}