package com.recc.recc_client.api.clients

import android.content.Context
import com.recc.recc_client.api.schema.MutableTrack
import com.recc.recc_client.api.schema.Track
import io.ktor.client.call.*
import kotlinx.coroutines.runBlocking

class TrackApiClient(context: Context) : BaseApiClient(context) {
    private val instance: AuthApiClient? = AuthApiClient.getInstance(context)

    fun getTracks(id: Int? = null): MutableList<Track> {
        var list: MutableList<Track> = mutableListOf()
        if (id != null) {
            var res: Track
            runBlocking {
                res = instance!!.request("GET", "tracks/$id").receive()
            }
            list.add(res)
            return list
        }
        runBlocking {
            list = instance!!.request("GET", "tracks").receive()
        }
        return list
    }

    fun addTrack(title: String, artist: String, duration: Int, genre: String): Unit {
        val track = MutableTrack(title, artist, duration, genre)
        runBlocking {
            instance!!.request("POST", "tracks", false, track)
        }
    }

    fun removeTrack(id: Int) = runBlocking {
        instance!!.request("DELETE", "tracks/$id", false)
    }

    fun updateTrack(id: Int, title: String, artist: String, duration: Int, genre: String) = runBlocking {
        val track = MutableTrack(title, artist, duration, genre)
        instance!!.request("PUT", "tracks/$id", false, track)
    }
}