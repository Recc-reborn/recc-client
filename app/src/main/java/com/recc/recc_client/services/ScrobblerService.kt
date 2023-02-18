package com.recc.recc_client.services

import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.Binder
import android.os.IBinder
import com.recc.recc_client.utils.Alert

class ScrobblerService: Service() {
    private val binder = LocalBinder()
    private val receiver = ScrobblerReceiver()

    inner class LocalBinder: Binder() {
        val service: ScrobblerService
            get() = this@ScrobblerService
    }

    override fun onDestroy() {
        applicationContext.unregisterReceiver(receiver)
    }

    override fun onBind(intent: Intent?): IBinder? {
        val intentFilter = IntentFilter()
        val SPOTIFY_PACKAGE = "com.spotify.music"
        val PLAYBACK_STATE_CHANGED = "$SPOTIFY_PACKAGE.playbackstatechanged"
        val QUEUE_CHANGED = "$SPOTIFY_PACKAGE.queuechanged"
        val METADATA_CHANGED = "$SPOTIFY_PACKAGE.metadatachanged"
        intentFilter.addAction(PLAYBACK_STATE_CHANGED)
        intentFilter.addAction(QUEUE_CHANGED)
        intentFilter.addAction(METADATA_CHANGED)
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

        Alert("creating receiver")
        applicationContext.registerReceiver(receiver, intentFilter)
        return binder
    }
}