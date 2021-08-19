package com.recc.recc_client.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.recc.recc_client.R
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.coroutines.*
import com.recc.recc_client.api.clients.AuthApiClient
import com.recc.recc_client.controllers.gui.TextBoxType
import com.recc.recc_client.controllers.gui.Textbox

class RegisterActivity : AppCompatActivity() {
    //private val authClient = AuthApiClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val context = applicationContext

        btnRegister.setOnClickListener {
            val name = Textbox(context, etRegisterName.text.toString(), TextBoxType.USERNAME)
            val email = Textbox(context, etRegisterEmail.text.toString(), TextBoxType.EMAIL)
            val password = Textbox(context, etRegisterPassword.text.toString(), TextBoxType.PASSWORD)
            val conPassword = Textbox(context, etRegisterConfirmPassword.text.toString(), TextBoxType.PASSWORD)
            println(password.value + " | " + conPassword.value)
            if (name.isValid() && email.isValid() && password.isValid() && conPassword.isValid() &&
                password.value == conPassword.value) {
                    //CoroutineScope(Dispatchers.Main).launch { authClient.register(name.value, email.value, password.value) }
                    Toast.makeText(context, "User successfully created!", Toast.LENGTH_LONG).show()
                    finish()
            } else if (password != conPassword) {
                Toast.makeText(context, "Passwords don't match", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(
                    context,
                    "Missing data, please fill out the empty text boxes",
                    Toast.LENGTH_LONG).show()
            }
        }
    }
}