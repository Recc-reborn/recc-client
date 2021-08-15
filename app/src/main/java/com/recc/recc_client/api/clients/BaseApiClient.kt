package com.recc.recc_client.api.clients

import android.content.res.Resources
import com.recc.recc_client.R
import com.recc.recc_client.contextFinder
import com.recc.recc_client.controllers.ContextFinder
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

// TODO 1: Add Header "Accepts: application/json" for all requests
open class BaseApiClient {
    private val context = contextFinder.context
    private val baseEndpoint = context.getString(R.string.api_base_endpoint)

    private val client = HttpClient(Android) {
        expectSuccess = false
        engine {
            connectTimeout = 10_000
        }
        install(JsonFeature) {
            serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                prettyPrint = true
                ignoreUnknownKeys = true
            })
        }
    }

    protected suspend fun request(methodName: String, endpoint: String, sendToken: Boolean = false, data: Any? = null): HttpResponse {
        val requestMethod : HttpMethod = when (methodName) {
            "GET" -> HttpMethod.Get
            "POST" -> HttpMethod.Post
            "PATCH" -> HttpMethod.Patch
            "DELETE" -> HttpMethod.Delete
            else -> throw Exception("Unsupported HTTP method: $methodName")
        }

        val apiEndpoint = "$baseEndpoint$endpoint"

        // TODO 2: get userToken
        val userToken = "SomeRandomStringThatShouldBeLoadedFromAFile"

        return client.request {
            url(apiEndpoint)
            method = requestMethod
            headers {
                if (sendToken) append("Authorization", "Bearer $userToken")
                // Note: I added [TODO 1] here, but I'm not sure if it works
                append(HttpHeaders.Accept, "application/json")
            }
            if (data != null) {
                body = data
            }
        }
    }
}