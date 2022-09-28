package com.recc.recc_client.http

import com.recc.recc_client.utils.Alert
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.HttpException
import java.net.SocketTimeoutException

private const val RETRY_TIME: Long = 3000

class ErrorInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        lateinit var response: Response
        val request = chain.request()
        var connection = false
        while (!connection) {
            try {
                response = chain.proceed(request)
                response.body?.let {
                    connection = true
                }
            } catch(e: SocketTimeoutException) {
                Thread.sleep(RETRY_TIME)
                Alert("No connection, retrying in ${ RETRY_TIME / 1000 } seconds...")
            } catch(e: HttpException) {
                // TODO: delete sharedPref token
                Alert("User not authenticated")
            }
        }
        return response
    }
}