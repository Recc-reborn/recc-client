package com.recc.recc_client.screens

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.recc.recc_client.R
import com.recc.recc_client.contextFinder
import com.recc.recc_client.controllers.gui.TextBoxType
import com.recc.recc_client.controllers.gui.Textbox
import kotlinx.android.synthetic.main.login_main.*

open class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_main)
        contextFinder.context = applicationContext

        btnLogin.setOnClickListener() {
            val emailTB = Textbox(etEmail.text.toString(), TextBoxType.EMAIL)
            val passTB = Textbox(etPassword.text.toString(), TextBoxType.PASSWORD)
            if (emailTB.isValid() && passTB.isValid())
                Toast.makeText(contextFinder.context, "Success!!!", Toast.LENGTH_LONG).show()
        }

        btnCreateAccount.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}