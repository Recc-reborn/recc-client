package com.recc.recc_client.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.recc.recc_client.R
import kotlinx.coroutines.*
import com.recc.recc_client.api.clients.AuthApiClient

class RegisterActivity : AppCompatActivity() {
    //private val authClient = AuthApiClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        val context = applicationContext

        val api = AuthApiClient(applicationContext)

    }
}