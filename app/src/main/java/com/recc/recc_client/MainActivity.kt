package com.recc.recc_client

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.coroutines.*

private val client = HttpClient(Android) {
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
    }

    // Method that is called once this main function ends, it calls the "desctructor" of our Ktor
    // client
    override fun onDestroy() {
        super.onDestroy()
        client.close()
    }
}

private suspend fun getTracks(view: View) {
    // Variable that stores a list of serializable Tracks which are gotten using a get request to
    // recc-server (everything is executed inside a coroutine using IO thread so that data
    // transfering gets easier)
    val track: List<Track> = withContext<List<Track>>(Dispatchers.IO) {
        client.get("http://10.0.2.2:8000/api/tracks")
    }
    // Coroutine which prints on console the data fetched from recc-server executed in the main
    // thread
    withContext<Unit>(Dispatchers.Main) {
        Toast.makeText(view.context, "id: $track.id\n$track", Toast.LENGTH_LONG)
        println("[Tracks]: " + track)
    }
}