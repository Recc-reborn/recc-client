package com.recc.recc_client

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.recc.recc_client.R
import com.recc.recc_client.databinding.ActivityWebViewBinding
import com.recc.recc_client.utils.Alert
import com.recc.recc_client.utils.SharedPreferences
import org.koin.android.ext.android.inject

class WebViewActivity: AppCompatActivity() {
    private lateinit var binding: ActivityWebViewBinding
    private val sharedPreferences: SharedPreferences by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Alert("getting into intent")
        binding = DataBindingUtil.setContentView(this, R.layout.activity_web_view)
        binding.wvWeb.settings.javaScriptEnabled = true
        val client = object: WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                Alert("url: ${request?.url}")
                request?.let {
                    if (it.url.toString().startsWith("http://localhost:8888/callback?code=")) {
                        val resultIntent = Intent()
                        resultIntent.putExtra("code", it.url.toString().replace("http://localhost:8888/callback?code=", ""))
                        setResult(Activity.RESULT_OK, resultIntent)
                        finish()
                        return true
                    }
                }
                return false
            }
        }

        binding.wvWeb.webViewClient = client
//        binding.wvWeb.loadUrl("https://www.last.fm/api/auth?api_key=${sharedPreferences.getToken()}")
        binding.wvWeb.loadUrl("https://accounts.spotify.com/authorize?client_id=3bf911bffb4b4058b6d8a790ade5c82a&redirect_uri=http://localhost:8888/callback&response_type=code")
    }
}