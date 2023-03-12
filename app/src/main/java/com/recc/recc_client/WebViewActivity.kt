package com.recc.recc_client

import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.recc.recc_client.databinding.ActivityWebViewBinding
import com.recc.recc_client.utils.*
import org.koin.android.ext.android.inject

class WebViewActivity: AppCompatActivity() {
    private lateinit var binding: ActivityWebViewBinding
    private val spotifyScopes = listOf(
        "playlist-modify-public",
        "playlist-modify-private",
        "playlist-read-collaborative",
        "playlist-read-private streaming",
        "app-remote-control",
        "user-read-currently-playing",
        "user-modify-playback-state",
        "user-read-playback-state",
        "user-library-modify")
    private val sharedPreferences: SharedPreferences by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val redirectUri = getString(R.string.spotify_redirect_uri)
        val clientId = getString(R.string.spotify_client_id)
        val spotifyUrl = "https://accounts.spotify.com/authorize?" +
                "client_id=${clientId}" +
                "&redirect_uri=${redirectUri}" +
                "&response_type=token" +
                "&scope=${spotifyScopes.joinToString(" ")}"

        binding = DataBindingUtil.setContentView(this, R.layout.activity_web_view)
        binding.wvWeb.settings.javaScriptEnabled = true
        val client = object: WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                Alert("url: ${request?.url}")
                request?.let {
                    if (it.url.toString().startsWith("${redirectUri}#access_token=")) {
                        val token = it.url.toString()
                            .replace("${redirectUri}#access_token=", "")
                            .replace("&token_type=Bearer&expires_in=3600", "")
                        sharedPreferences.saveSpotifyToken(token)
                        finish()
                        return true
                    }
                }
                return false
            }
        }

        binding.wvWeb.webViewClient = client
//        binding.wvWeb.loadUrl("https://www.last.fm/api/auth?api_key=${sharedPreferences.getToken()}")

        Alert("url: $spotifyUrl")
        binding.wvWeb.loadUrl(spotifyUrl)
    }
}