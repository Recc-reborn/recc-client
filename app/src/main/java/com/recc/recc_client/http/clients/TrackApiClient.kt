package com.recc.recc_client.http.clients

import android.content.Context
import com.recc.recc_client.http.schema.MutableTrack
import com.recc.recc_client.http.schema.Track
import io.ktor.client.call.*
import kotlinx.coroutines.runBlocking

class TrackApiClient(context: Context) {
    private val instance = BaseApiClient.getInstance(context)

    fun getTracks(id: Int? = null): MutableList<Track>? {
        var list: MutableList<Track> = mutableListOf()
        if (id != null) {
            var res: Track
            runBlocking {
                instance.request("GET", "tracks/$id")?.also {
                    list.add(it.receive())
                }
            }
        }
        runBlocking {
            instance.request("GET", "tracks")?.also { list = it.receive() }
        }
        return list
    }

    fun addTrack(title: String, artist: String, duration: Int, genre: String): Unit {
        val track = MutableTrack(title, artist, duration, genre)
        runBlocking {
            instance.request("POST", "tracks", instance.token, track)
        }
    }

    fun removeTrack(id: Int) = runBlocking {
        instance.request("DELETE", "tracks/$id", instance.token)
    }

    fun updateTrack(id: Int, title: String, artist: String, duration: Int, genre: String) = runBlocking {
        val track = MutableTrack(title, artist, duration, genre)
        instance.request("PUT", "tracks/$id", instance.token, track)
    }
}