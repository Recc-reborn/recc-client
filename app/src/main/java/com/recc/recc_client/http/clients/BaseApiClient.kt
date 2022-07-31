package com.recc.recc_client.http.clients

import android.content.Context
import android.widget.Toast
import com.google.gson.Gson
import com.recc.recc_client.R
import com.recc.recc_client.http.schema.UserInfo
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.*
import java.io.File

open class BaseApiClient (context: Context) {
    var token: String? = null
    var userInfo: UserInfo? = null
    private val baseEndpoint = context.getString(R.string.api_base_endpoint)
    private val client = HttpClient(Android) {
        expectSuccess = false
        engine {
            connectTimeout = 10_000
        }
        install(JsonFeature) {
            serializer = GsonSerializer() {
                setPrettyPrinting()
                disableHtmlEscaping()
            }
        }
    }
    init {
        // read token
        val tokenFile = File(context.filesDir, "token.tmp")
        if (tokenFile.exists())
            this.token = token
        // read user info
        val infoFile = File(context.filesDir, context.getString(R.string.user_info_path))
        if (infoFile.exists()) {
            val bufferedReader = infoFile.bufferedReader()
            val json = bufferedReader.readText()
            val gson = Gson()
            this.userInfo = gson.fromJson(json, UserInfo::class.java)
        }
    }
    companion object {
        private var instance: BaseApiClient? = null
        fun getInstance(context: Context): BaseApiClient = BaseApiClient.instance
            ?: synchronized(this) {
                BaseApiClient.instance ?: BaseApiClient(context).also {
                    BaseApiClient.instance = it
                }
            }
    }

    suspend fun request(methodName: String,
                        endpoint: String,
                        sendToken: String? = null,
                        data: Any? = null,
                        context: Context? = null): HttpResponse? {
        val requestMethod: HttpMethod = when (methodName) {
            "GET" -> HttpMethod.Get
            "POST" -> HttpMethod.Post
            "PATCH" -> HttpMethod.Patch
            "PUT" -> HttpMethod.Put
            "DELETE" -> HttpMethod.Delete
            else -> throw Exception("Unsupported HTTP method: $methodName")
        }
        val apiEndpoint = "$baseEndpoint$endpoint"
        val userToken = sendToken ?: ""

        try {
            return withContext(Dispatchers.IO) {
                client.request {
                    url(apiEndpoint)
                    method = requestMethod
                    headers {
                        sendToken?.also { append("Authorization", "Bearer $userToken") }
                        append(HttpHeaders.Accept, "application/json")
                    }
                    if (data != null) {
                        contentType(ContentType.Application.Json)
                        body = data
                    }
                }
            }
        } catch (e: Throwable) {
            println("[HTTP request error] $e")
            context?.also {
                CoroutineScope(Dispatchers.Main).launch {
                    Toast.makeText(it, "[HTTP request error] $e", Toast.LENGTH_LONG).show()
                }
            }
        }
        return null
    }
}