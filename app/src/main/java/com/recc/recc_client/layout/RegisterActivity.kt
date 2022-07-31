package com.recc.recc_client.layout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.recc.recc_client.R
import com.recc.recc_client.http.clients.AuthApiClient

class RegisterActivity : AppCompatActivity() {
    //private val authClient = AuthApiClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val context = applicationContext

        val api = AuthApiClient(applicationContext)

    }
}