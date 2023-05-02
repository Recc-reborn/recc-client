package com.recc.recc_client

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import com.recc.recc_client.databinding.ActivityMainBinding
import com.recc.recc_client.layout.settings.SettingsScreenEvent
import com.recc.recc_client.layout.settings.SettingsViewModel
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
    private lateinit var scrobblerService: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        logoutFromSpotify()
        Status( "Launching Main Activity...")
    }

    fun loginToSpotify(vm: SettingsViewModel) {
        SpotifyAppRemote.connect(this, spotifyConnectionParams, object: Connector.ConnectionListener {
            override fun onConnected(spotifyAppRemote: SpotifyAppRemote?) {
                sharedPreferences.saveSpotifyStatus(true)
                vm.setLogin()
                (applicationContext as ReccApplication).apply {
                    spotifyApi = spotifyAppRemote
                    spotifyApi?.playerApi?.subscribeToPlayerState()?.setEventCallback { playerState ->
                        Intent("com.spotify.music.playbackstatechanged").also {
                            it.putExtra("track", playerState.track.name)
                            it.putExtra("artist", playerState.track.artist.name)
                            it.putExtra("album", playerState.track.album.name)
                            it.putExtra("isPaused", playerState.isPaused)
                            it.putExtra("position", playerState.playbackPosition)
                            sendBroadcast(it)
                        }
                    }
                }
                // Creates scrobbler
                scrobblerService = Intent(applicationContext, ScrobblerService::class.java)
                startService(scrobblerService)
            }

            override fun onFailure(error: Throwable?) {
                Status("not connected to spotify: $error")
                vm.spotifyNotInstalled()
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
                    stopService(scrobblerService)
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
