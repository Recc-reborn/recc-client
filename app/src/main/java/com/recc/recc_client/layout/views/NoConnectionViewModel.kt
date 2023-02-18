package com.recc.recc_client.layout.views

import com.recc.recc_client.http.InterceptorViewModel
import com.recc.recc_client.http.impl.Auth
import com.recc.recc_client.layout.common.BaseEventViewModel
import com.recc.recc_client.utils.SharedPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoConnectionViewModel(
    private val viewModel: InterceptorViewModel,
    private val http: Auth,
    private val sharedPreferences: SharedPreferences
): BaseEventViewModel<NoConnectionScreenEvent>() {
    fun tryForConnection() {
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.retryConnection()
            val token = sharedPreferences.getToken()
            if (!token.isNullOrBlank()) {
                http.me(token)
            } else {
                postEvent(NoConnectionScreenEvent.NoAccount)
            }
        }

    }
}