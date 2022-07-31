package com.recc.recc_client.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.recc.recc_client.R
import com.recc.recc_client.http.clients.AuthApiClient
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.runBlocking

class LoginFragment : Fragment(R.layout.fragment_login) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = requireActivity().applicationContext
        val api = AuthApiClient(context)
        loginButton.setOnClickListener {
            runBlocking {
                api.login(context, loginEmailAddressEditText.text.toString(), loginPasswordEditText.text.toString())
             }
        }
    }
}