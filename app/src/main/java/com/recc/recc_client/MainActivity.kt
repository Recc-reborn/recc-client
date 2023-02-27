package com.recc.recc_client

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.recc.recc_client.databinding.ActivityMainBinding
import com.recc.recc_client.services.ScrobblerService
import com.recc.recc_client.utils.SharedPreferences
import com.recc.recc_client.utils.Status
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector
import com.spotify.android.appremote.api.SpotifyAppRemote
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import se.michaelthelin.spotify.SpotifyApi
import java.net.URI

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val sharedPreferences: SharedPreferences by inject()
    private val spotifyConnectionParams: ConnectionParams by inject()
    private lateinit var scrobblerService: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        Status( "Launching Main Activity...")
    }

    fun loginToSpotify() {
        (applicationContext as ReccApplication).spotifyApi = SpotifyApi.Builder()
            .setClientId(getString(R.string.spotify_client_id))
            .setRedirectUri(URI(getString(R.string.spotify_redirect_uri)))
            .build()
//        CoroutineScope(Dispatchers.IO).launch {
//
//        }
    }

    fun logoutFromSpotify() {
        (applicationContext as ReccApplication).spotifyApiOld?.let { api ->
            if (api.isConnected) {
                Status("Spotify disconnected")
                (applicationContext as ReccApplication).spotifyApiOld = null
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
