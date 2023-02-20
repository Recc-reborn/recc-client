package com.recc.recc_client.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.recc.recc_client.http.impl.Control
import com.recc.recc_client.utils.Alert
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class ScrobblerReceiver: BroadcastReceiver() {
    private var positionFirst: Long = 0
    private var playing = ""
    private var playback = false
    private val playbackTime: Long = 30000

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent == null)
            return
        when (intent.action) {
            "com.spotify.music.playbackstatechanged" -> {
                val track = intent.getStringExtra("track")
                val artist = intent.getStringExtra("artist")
                val album = intent.getStringExtra("album")
                val isPaused = intent.getBooleanExtra("isPaused", true)
                val position = intent.getLongExtra("position", 0L)
                if (playing != "$track-$artist-$album") {
                    positionFirst = position
                    playback = false
                    playing = "$track-$artist-$album"
                } else if (position - positionFirst > playbackTime && !playback && !isPaused) {
                    playback = true
                    makePlayback(track.orEmpty(), album.orEmpty(), artist.orEmpty())
                } else if (position < positionFirst) {
                    positionFirst = position
                }
                Alert("player: $track - $artist - $album - $position - $isPaused - ${position - positionFirst} - $playback - $position - $positionFirst")
            }
        }
    }

    private fun makePlayback(track: String, album: String, artist: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val search = "$track $artist $album"

        }
    }
}