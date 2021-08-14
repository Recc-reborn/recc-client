package com.recc.recc_client.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.recc.recc_client.R
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.coroutines.*
import com.recc.recc_client.api.clients.AuthApiClient

class Register : AppCompatActivity() {
    private val authClient = AuthApiClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btnRegister.setOnClickListener {
            val name: String = etRegisterName.text.toString()
            val email: String = etRegisterEmail.text.toString()
            val password: String = etRegisterPassword.text.toString()
            val conPassword: String = etRegisterConfirmPassword.text.toString()

            if (notEmptyTextBoxes() && password == conPassword) {
                CoroutineScope(Dispatchers.Main).launch { authClient.register(name, email, password) }
                Toast.makeText(this, "User successfully created!", Toast.LENGTH_LONG).show()
                finish()
            } else if (password != conPassword) {
                Toast.makeText(this, "Passwords don't match", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(
                    this,
                    "Missing data, please fill out the empty text boxes",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun notEmptyTextBoxes() = (
        etRegisterConfirmPassword.text.isNotEmpty()
            && etRegisterName.text.isNotEmpty()
            && etRegisterEmail.text.isNotEmpty()
            && etRegisterPassword.text.isNotEmpty()
        )
}