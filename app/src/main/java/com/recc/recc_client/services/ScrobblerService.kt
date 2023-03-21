package com.recc.recc_client.services

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Binder
import android.os.IBinder
import android.widget.Toast
import com.recc.recc_client.http.impl.Control
import com.recc.recc_client.layout.common.onFailure
import com.recc.recc_client.layout.common.onSuccess
import com.recc.recc_client.utils.Alert
import com.recc.recc_client.utils.SharedPreferences
import com.recc.recc_client.utils.Status
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class ScrobblerService: Service() {
    private val binder = LocalBinder()
    private val receiver = ScrobblerReceiver()
    private val control: Control by inject()
    private val sharedPreferences: SharedPreferences by inject()

    override fun onDestroy() {
        applicationContext.unregisterReceiver(receiver)
    }

    override fun onCreate() {
        val intentFilter = IntentFilter()
        intentFilter.addAction("com.spotify.music.playbackstatechanged")
        intentFilter.addAction("com.spotify.music.queuechanged")
        intentFilter.addAction("com.spotify.music.metadatachanged")
        intentFilter.addAction("com.android.music.musicservicecommand")
        intentFilter.addAction("com.android.music.playstatechanged")
        intentFilter.addAction("com.android.music.metachanged")
        intentFilter.addAction("com.android.music.metachanged")
        intentFilter.addAction("com.android.music.playstatechanged")
        intentFilter.addAction("com.android.music.playbackcomplete")
        intentFilter.addAction("com.android.music.queuechanged")
        intentFilter.addAction("com.htc.music.metachanged")
        intentFilter.addAction("fm.last.android.metachanged")
        intentFilter.addAction("com.sec.android.app.music.metachanged")
        intentFilter.addAction("com.nullsoft.winamp.metachanged")
        intentFilter.addAction("com.amazon.mp3.metachanged")
        intentFilter.addAction("com.miui.player.metachanged")
        intentFilter.addAction("com.real.IMP.metachanged")
        intentFilter.addAction("com.sonyericsson.music.metachanged")
        intentFilter.addAction("com.rdio.android.metachanged")
        intentFilter.addAction("com.samsung.sec.android.MusicPlayer.metachanged")
        intentFilter.addAction("com.andrew.apollo.metachanged")

        Status("Creating receiver...")
        applicationContext.registerReceiver(receiver, intentFilter)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }

    private fun makePlayback(track: String, album: String, artist: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val search = "$track $artist $album"
            control.fetchTracks(search = search)
                .onSuccess { tracks ->
                    val firstTrack = tracks.first()
                    if (firstTrack.title.lowercase().filter { !it.isWhitespace() } == track.lowercase().filter { !it.isWhitespace() }
                        && firstTrack.artist.filter { !it.isWhitespace() } == artist.filter { !it.isWhitespace() }) {
                        CoroutineScope(Dispatchers.IO).launch {
                            control.createPlayback(sharedPreferences.getToken(), firstTrack.id)
                                .onSuccess {
                                    Alert("Playback created: $it")
                                }.onFailure {
                                    Alert("Playback not craeted: $it")
                                }
                        }
                    } else {
                        Alert("track not found: $firstTrack / $search")
                    }
                }
        }
    }

    inner class LocalBinder: Binder() {
        val service: ScrobblerService
            get() = this@ScrobblerService
    }

    inner class ScrobblerReceiver: BroadcastReceiver() {
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
                        Toast.makeText(context, "Now playing: $track by $artist", Toast.LENGTH_SHORT).show()
                    } else if (position - positionFirst > playbackTime && !playback && !isPaused) {
                        playback = true
                        makePlayback(track.orEmpty(), album.orEmpty(), artist.orEmpty())
                    } else if (position < positionFirst) {
                        positionFirst = position
                    }
                }
            }
        }
    }
}