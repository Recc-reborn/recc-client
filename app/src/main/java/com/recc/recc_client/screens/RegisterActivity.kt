package com.recc.recc_client.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.recc.recc_client.R
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.coroutines.*
import com.recc.recc_client.api.clients.AuthApiClient
import com.recc.recc_client.contextFinder
import com.recc.recc_client.controllers.gui.TextBoxType
import com.recc.recc_client.controllers.gui.Textbox

class RegisterActivity : AppCompatActivity() {
    private val authClient = AuthApiClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        contextFinder.context = applicationContext

        btnRegister.setOnClickListener {
            val name = Textbox(etRegisterName.text.toString(), TextBoxType.USERNAME)
            val email = Textbox(etRegisterEmail.text.toString(), TextBoxType.EMAIL)
            val password = Textbox(etRegisterPassword.text.toString(), TextBoxType.PASSWORD)
            val conPassword = Textbox(etRegisterConfirmPassword.text.toString(), TextBoxType.PASSWORD)
            println(password.value + " | " + conPassword.value)
            if (name.isValid() && email.isValid() && password.isValid() && conPassword.isValid() &&
                password.value == conPassword.value) {
                    //CoroutineScope(Dispatchers.Main).launch { authClient.register(name.value, email.value, password.value) }
                    Toast.makeText(contextFinder.context, "User successfully created!", Toast.LENGTH_LONG).show()
                    finish()
            } else if (password != conPassword) {
                Toast.makeText(contextFinder.context, "Passwords don't match", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(
                    contextFinder.context,
                    "Missing data, please fill out the empty text boxes",
                    Toast.LENGTH_LONG).show()
            }
        }
    }
}