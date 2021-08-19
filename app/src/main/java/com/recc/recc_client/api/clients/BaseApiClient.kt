package com.recc.recc_client.api.clients

import android.content.Context
import com.recc.recc_client.R
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

open class BaseApiClient constructor(context: Context) {
    companion object {
        private var instance: BaseApiClient? = null
        fun getInstance(context: Context): BaseApiClient = BaseApiClient.instance.also { println("VIEJO") }
            ?: synchronized(this) {
                BaseApiClient.instance ?: BaseApiClient(context).also {
                    println("NUEVO")
                    BaseApiClient.instance = it
                }
            }
    }
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

    suspend fun request(methodName: String, endpoint: String, sendToken: Boolean = false, data: Any? = null): HttpResponse {
        val requestMethod : HttpMethod = when (methodName) {
            "GET" -> HttpMethod.Get
            "POST" -> HttpMethod.Post
            "PATCH" -> HttpMethod.Patch
            "PUT" -> HttpMethod.Put
            "DELETE" -> HttpMethod.Delete
            else -> throw Exception("Unsupported HTTP method: $methodName")
        }

        val apiEndpoint = "$baseEndpoint$endpoint"
        println("ENDPOINT: " + apiEndpoint)

        // TODO 2: get userToken
        val userToken = "SomeRandomStringThatShouldBeLoadedFromAFile"

        return withContext(Dispatchers.IO) {
            client.request {
                url(apiEndpoint)
                method = requestMethod
                headers {
                    if (sendToken)
                        append("Authorization", "Bearer $userToken")
                    append(HttpHeaders.Accept, "application/json")
                }
                if (data != null) {
                    contentType(ContentType.Application.Json)
                    body = data
                }
            }
        }
    }
}