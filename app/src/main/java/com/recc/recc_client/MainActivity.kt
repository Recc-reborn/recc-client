package com.recc.recc_client

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.recc.recc_client.databinding.ActivityMainBinding
import com.recc.recc_client.services.ScrobblerService
import com.recc.recc_client.utils.Alert
import com.recc.recc_client.utils.SharedPreferences
import com.recc.recc_client.utils.Status
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector
import com.spotify.android.appremote.api.SpotifyAppRemote
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val sharedPreferences: SharedPreferences by inject()
    private val spotifyConnectionParams: ConnectionParams by inject()
    private lateinit var scrobblerService: ScrobblerService
    private val serviceConnection = object: ServiceConnection {
        override fun onServiceConnected(componentName: ComponentName, iBinder: IBinder?) {
            Alert("connected")
            val binder = iBinder as ScrobblerService.LocalBinder
            scrobblerService = binder.service
        }
        override fun onServiceDisconnected(componentName: ComponentName) {
            Alert("disconnected")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        Status( "Launching Main Activity...")
    }

    fun loginToSpotify() {
        SpotifyAppRemote.connect(applicationContext, spotifyConnectionParams, object: Connector.ConnectionListener {
            override fun onConnected(spotifyAppRemote: SpotifyAppRemote?) {
                Status("connected to spotify")
                (applicationContext as ReccApplication).spotifyApi = spotifyAppRemote
                sharedPreferences.saveSpotifyStatus(true)
                (applicationContext as ReccApplication).spotifyApi?.playerApi?.subscribeToPlayerState()?.setEventCallback { playerState ->
                    Intent("com.spotify.music.playbackstatechanged").also {
                        it.putExtra("track", playerState.track.name)
                        it.putExtra("artist", playerState.track.artist.name)
                        it.putExtra("album", playerState.track.album.name)
                        it.putExtra("isPaused", playerState.isPaused)
                        it.putExtra("position", playerState.playbackPosition)
                        sendBroadcast(it)
                    }
                }
                // Creates scrobbler
                Intent(applicationContext, ScrobblerService::class.java).also {
                    bindService(it, serviceConnection, Context.BIND_AUTO_CREATE)
                }
            }

            override fun onFailure(error: Throwable?) {
                Status("not connected to spotify: $error")
            }
        })
    }

    fun logoutFromSpotify() {
        (applicationContext as ReccApplication).spotifyApi?.let {api ->
            if (api.isConnected) {
                Status("Spotify disconnected")
                (applicationContext as ReccApplication).spotifyApi = null
                sharedPreferences.saveSpotifyStatus(false)
                SpotifyAppRemote.disconnect(api)
                if (sharedPreferences.getSpotifyStatus()) {
                    unbindService(serviceConnection)
                }
            }
        }
    }

    fun enableNoConnectionView() {
        binding.noConnectionView.show()
    }

    fun disableNoConnectionView() {
        binding.noConnectionView.hide()
    }

    fun enableLoadingBar() {
        binding.lbMain.visibility = View.VISIBLE
    }

    fun disableLoadingBar() {
        binding.lbMain.visibility = View.GONE
    }
}
