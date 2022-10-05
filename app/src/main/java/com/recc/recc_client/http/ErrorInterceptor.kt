package com.recc.recc_client.http

import android.content.Context
import com.recc.recc_client.R
import com.recc.recc_client.layout.user_msg.UserMsgViewModel
import com.recc.recc_client.utils.Alert
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import java.net.SocketTimeoutException

private const val RETRY_TIME: Long = 3000
private const val ATTEMPTS = 3
private const val INTERNAL_SERVER_ERROR_CODE = 500

class ErrorInterceptor(
    private val context: Context,
    private val viewModel: UserMsgViewModel
): Interceptor {

    private var retryAttempts = 1

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var response = Response.Builder()
            .code(INTERNAL_SERVER_ERROR_CODE)
            .body("{\"message\":\"No internet connection\"}".toResponseBody(null))
            .protocol(Protocol.HTTP_2)
            .message("No internet connection")
            .request(request)
            .build()
        var connection = false
        while (!connection && retryAttempts <= ATTEMPTS) {
            try {
                response = chain.proceed(request)
                response.body?.let {
                    connection = true
                }
            } catch(e: SocketTimeoutException) {
                Thread.sleep(RETRY_TIME)
                if (retryAttempts < ATTEMPTS) {
                    viewModel.postMessage(context.getString(R.string.no_connection_interceptor_msg, retryAttempts, ATTEMPTS, RETRY_TIME / 1000))
                }
            } catch(e: HttpException) {
                // TODO: delete sharedPref token
                Alert("User not authenticated")
            }
            retryAttempts++
        }
        if (retryAttempts == ATTEMPTS + 1) {
            viewModel.handleNoConnection()

        }
        return response
    }
}