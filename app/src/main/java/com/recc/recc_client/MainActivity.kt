package com.recc.recc_client

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

val RECC_SERVER = "http://10.0.2.2:8000"
val CLIENT = HttpClient(Android) {
    install(JsonFeature) {
        serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
            prettyPrint = true
            ignoreUnknownKeys = true
        })
    }
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Event that gets triggered every time login button is pressed
        btnLogin.setOnClickListener { view ->
            // Coroutine scope, it is used to call a suspend function i.e. those that can call
            // multiple coroutines inside it
            CoroutineScope(Dispatchers.IO).launch {
                getTracks(view)
            }
        }

        // Every time "create account" button is pressed a sub-activity called "Register" is called
        // which it has its own form and it will be used to send a post petition to recc-server to create
        // a new account
        btnCreateAccount.setOnClickListener { view ->
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }
    }

    // Method that is called once this main function ends, it calls the "desctructor" of our Ktor
    // client
    override fun onDestroy() {
        super.onDestroy()
        CLIENT.close()
    }
}

private suspend fun getTracks(view: View) {
    // Variable that stores a list of serializable Tracks which are gotten using a get request to
    // recc-server (everything is executed inside a coroutine using IO thread so that data
    // transfering gets easier)
    val track: List<Track> = withContext<List<Track>>(Dispatchers.IO) {
        CLIENT.get("$RECC_SERVER/api/tracks")
    }
    // Coroutine which prints on console the data fetched from recc-server executed in the main
    // thread
    withContext<Unit>(Dispatchers.Main) {
        Toast.makeText(view.context, "id: $track.id\n$track", Toast.LENGTH_LONG).show()
    }
}