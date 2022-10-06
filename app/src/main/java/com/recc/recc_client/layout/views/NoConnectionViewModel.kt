package com.recc.recc_client.layout.views

import android.content.Context
import com.recc.recc_client.R
import com.recc.recc_client.http.InterceptorViewModel
import com.recc.recc_client.http.impl.Auth
import com.recc.recc_client.layout.common.BaseEventViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoConnectionViewModel(
    private val context: Context,
    private val viewModel: InterceptorViewModel,
    private val http: Auth
): BaseEventViewModel<NoConnectionScreenEvent>() {
    fun tryForConnection() {
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.retryConnection()
            val token = getToken()
            if (!token.isNullOrBlank()) {
                http.me(token)
            } else {
                postEvent(NoConnectionScreenEvent.NoAccount)
            }
        }

    }

    private fun getToken(): String? {
        val sharedPref = context.getSharedPreferences(context.getString(R.string.preference_auth_key_file), Context.MODE_PRIVATE)
        return sharedPref.getString(context.getString(R.string.auth_token_key), null)
    }
}