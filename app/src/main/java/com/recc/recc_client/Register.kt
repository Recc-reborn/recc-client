package com.recc.recc_client

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.view.*
import kotlinx.coroutines.*

private val MISSING_KEYS_CODE: Int = 422
private val REGISTER_ROLE: String = "user"
private val url: String = "$RECC_SERVER/api/users"

class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btnRegister.setOnClickListener {
            val name: String = etRegisterName.text.toString()
            val email: String = etRegisterEmail.text.toString()
            val password: String = etRegisterPassword.text.toString()
            val conPassword: String = etRegisterConfirmPassword.text.toString()
            if (notEmptyTextBoxes() && password == conPassword) {
                val user = User(name, email, password, REGISTER_ROLE)
                CoroutineScope(Dispatchers.Main).launch { registerUser(user) }
                Toast.makeText(this, "User successfully created!!", Toast.LENGTH_LONG).show()
                finish()
            }
            else if (password != conPassword)
                Toast.makeText(this, "Passwords don't match", Toast.LENGTH_LONG).show()
            else {
                Toast.makeText(this, "Missing data, please fill out the empty text boxes",
                    Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun notEmptyTextBoxes() = (etRegisterConfirmPassword.text.length != 0
            && etRegisterName.text.length != 0 && etRegisterEmail.text.length != 0
            && etRegisterPassword.text.length != 0)
}

private suspend fun registerUser(user: User) {

    withContext(Dispatchers.IO) {
        CLIENT.post<Unit>(url) {
            contentType(ContentType.Application.Json)
            body = user
        }
    }
}