package com.recc.recc_client.layout.views

import android.content.Context
import androidx.lifecycle.ViewModel
import com.recc.recc_client.R
import com.recc.recc_client.http.impl.Auth
import com.recc.recc_client.layout.common.onFailure
import com.recc.recc_client.layout.common.onSuccess
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoConnectionViewModel(
    private val context: Context,
    private val http: Auth
): ViewModel() {


    fun getToken(): String? {
        val sharedPref = context.getSharedPreferences(context.getString(R.string.preference_auth_key_file), Context.MODE_PRIVATE)
        return sharedPref.getString(context.getString(R.string.auth_token_key), null)
    }

    fun tryForConnection() {
        CoroutineScope(Dispatchers.IO).launch {
            getToken()?.let { token ->
                http.me(token)
                    .onSuccess {

                    }
                    .onFailure {}
            }
        }
    }
}