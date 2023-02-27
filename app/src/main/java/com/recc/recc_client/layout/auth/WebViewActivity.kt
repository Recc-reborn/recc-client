package com.recc.recc_client.layout.auth

import android.os.Bundle
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
        binding.wvWeb.loadUrl("https://www.last.fm/api/auth?api_key=${sharedPreferences.getToken()}")
    }
}