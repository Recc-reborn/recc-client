package com.recc.recc_client.http

import android.content.Context
import com.recc.recc_client.R
import com.recc.recc_client.di.TIMEOUT
import com.recc.recc_client.layout.user_msg.UserMsgViewModel
import com.recc.recc_client.utils.Alert
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import java.net.SocketTimeoutException

private const val ATTEMPTS = 3
private const val INTERNAL_SERVER_ERROR_CODE = 500

class ErrorInterceptor(
    private val context: Context,
    private val viewModel: InterceptorViewModel,
    private val msgViewModel: UserMsgViewModel): Interceptor {
    private var retryAttempts = 1

    init {
        viewModel.retry.observeForever {
            retryAttempts = 1
        }
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var response = Response.Builder()
            .code(INTERNAL_SERVER_ERROR_CODE)
            .body("{\"message\":\"No internet connection\"}".toResponseBody(null))
            .protocol(Protocol.HTTP_2)
            .message("No internet connection")
            .request(request)
            .build()
        while (retryAttempts <= ATTEMPTS) {
            try {
                response = chain.proceed(request)
                response.body?.let {
                    viewModel.setIsConnected(true)
                }
                break
            } catch(e: SocketTimeoutException) {
                viewModel.setIsConnected(false)
                Thread.sleep(TIMEOUT * 1000)
                if (retryAttempts < ATTEMPTS) {
                    msgViewModel.postMessage(context.getString(R.string.no_connection_interceptor_msg, retryAttempts, ATTEMPTS, TIMEOUT))
                }
            } catch(e: HttpException) {
                viewModel.setIsConnected(false)
                // TODO: delete sharedPref token
                Alert("User not authenticated")
            }
            retryAttempts++
        }
        if (viewModel.connection.value != true) {
            Alert("retry: $retryAttempts")
            msgViewModel.handleNoConnection()
        }
        return response
    }
}