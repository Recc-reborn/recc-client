package com.recc.recc_client.http.clients

import android.content.Context
import com.recc.recc_client.http.schema.MutableTrack
import kotlinx.coroutines.runBlocking

class PlaybackApiClient(context: Context) : BaseApiClient(context) {
    private val instance = BaseApiClient.getInstance(context)

    // TODO: Implement these methods correctly using the authentication token
    fun addPlayback(title: String, artist: String, duration: Int, genre: String): Unit {
        val track = MutableTrack(title, artist, duration, genre)
        runBlocking {
            instance!!.request("POST", "tracks", instance.token, track)
        }
    }

    fun removePlayback(id: Int) = runBlocking {
        instance!!.request("DELETE", "tracks/$id", instance.token)
    }
}