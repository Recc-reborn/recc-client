package com.recc.recc_client

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.auth.*
import io.ktor.client.features.auth.providers.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.http.*
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
private val loginUrl: String = "$RECC_SERVER/api/auth/token/"
private val infoUrl: String = "$RECC_SERVER/api/users/me"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Event that gets triggered every time login button is pressed
        btnLogin.setOnClickListener { view ->
            // Coroutine scope, it is used to call a suspend function i.e. those that can call
            // multiple coroutines inside it
            val email: String = etEmail.text.toString()
            val password: String = etPassword.text.toString()
            CoroutineScope(Dispatchers.IO).launch {
                login(view, email, password)
            }
        }

        // Every time "create account" button is pressed a sub-activity called "Register" is called
        // which it has its own form and it will be used to send a post petition to recc-server to create
        // a new account
        btnCreateAccount.setOnClickListener {
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

private suspend fun login(view: View, email: String, password: String) {
    val token  = withContext<String>(Dispatchers.IO) {
        CLIENT.post<String>(loginUrl) {
            contentType(ContentType.Application.Json)
            body = UserLogin(email, password, Build.DEVICE)
        }
    }

    val info = withContext<UserInfo>(Dispatchers.IO) {
        val loginClient = HttpClient(Android) {
            install(JsonFeature) {
                serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                    prettyPrint = true
                    ignoreUnknownKeys = true
                })
            }
            install(Auth) {
                bearer {
                    loadTokens {  BearerTokens(accessToken = token, refreshToken = token) }
                }
            }
        }

        loginClient.get<UserInfo>(infoUrl) {
            headers {
                append(HttpHeaders.Authorization, token)
            }
        }
    }

    withContext<Unit>(Dispatchers.Main) {
        Toast.makeText(view.context, "Welcome back ${info.name}!!", Toast.LENGTH_LONG).show()
    }
}