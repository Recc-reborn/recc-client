package com.recc.recc_client.screens

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.recc.recc_client.R
import com.recc.recc_client.api.clients.BaseApiClient
import com.recc.recc_client.api.schema.Track
import com.recc.recc_client.controllers.gui.TextBoxType
import com.recc.recc_client.controllers.gui.Textbox
import io.ktor.client.call.*
import kotlinx.android.synthetic.main.login_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

open class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_main)

        btnLogin.setOnClickListener() {
            val emailTB = Textbox(applicationContext, etEmail.text.toString(), TextBoxType.EMAIL)
            val passTB = Textbox(applicationContext, etPassword.text.toString(), TextBoxType.PASSWORD)
            val baseApi = BaseApiClient.getInstance(applicationContext)
            //if (emailTB.isValid() && passTB.isValid())
            CoroutineScope(Dispatchers.IO).launch {
                //Toast.makeText(applicationContext, baseApi.request("GET", "tracks/1").toString(), Toast.LENGTH_LONG).show()
                val track: Track = baseApi.request("GET", "tracks/1").receive()
                println("RESULTS: " + track.artist)
            }
        }

        btnCreateAccount.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}