package com.recc.recc_client2

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

        btnLogin.setOnClickListener { view ->
            CoroutineScope(Dispatchers.IO).launch {
                getTracks(view)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        client.close()
    }
}

fun log (message: String) = println("[${Thread.currentThread().name}]: $message")

suspend fun getApiResult1 (): String {
    log("Get result from suspend function 1")
    delay(3000) // milisegundos
    return "result 1"
}

suspend fun getApiResult2 (): String {
    log("Get result from suspend function 2")
    delay(1500) // milisegundosS
    return "result 2"
}

suspend fun executeRequest() = withContext(Dispatchers.IO) {
    val result1 = getApiResult1()
    println(result1)
    val result2 = getApiResult2()
    println(result2)
}

private suspend fun getTracks(view: View) {
    val track: List<Track> = withContext<List<Track>>(Dispatchers.IO) {
        client.get("http://10.0.2.2:8000/api/tracks")
    }
    withContext<Unit>(Dispatchers.Main) {
        Toast.makeText(view.context, "id: $track.id\n$track", Toast.LENGTH_LONG)
        println("[Track]: " + track)
    }
}