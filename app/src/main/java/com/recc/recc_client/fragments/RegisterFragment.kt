package com.recc.recc_client.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.Fragment
import com.recc.recc_client.R
import kotlinx.android.synthetic.main.fragment_register.*
import java.util.regex.Pattern

class RegisterFragment : Fragment(R.layout.fragment_register) {
    var emailValid = false
    var usernameValid = false
    var passwordValid = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun enableButton() = (emailValid && usernameValid && passwordValid)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerButton.isEnabled = false

        registerEmailAddressEditText.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                emailValid = android.util.Patterns.EMAIL_ADDRESS.matcher(p0).matches()
                if (!emailValid)
                    registerEmailAddressEditText.error = "Email not valid"
                registerButton.isEnabled = enableButton()
            }

            override fun afterTextChanged(p0: Editable?) {
                emailValid = android.util.Patterns.EMAIL_ADDRESS.matcher(p0).matches()
                if (!emailValid)
                    registerEmailAddressEditText.error = "Email not valid"
                registerButton.isEnabled = enableButton()
            }

        })

        registerUserNameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                usernameValid = Pattern.compile(getString(R.string.regex_username)).matcher(p0).matches()
                if (!usernameValid)
                    registerUserNameEditText.error = "Username is not valid"
                registerButton.isEnabled = enableButton()
            }

            override fun afterTextChanged(p0: Editable?) {
                usernameValid = Pattern.compile(getString(R.string.regex_username)).matcher(p0).matches()
                if (!usernameValid)
                    registerUserNameEditText.error = "Username is not valid"
                registerButton.isEnabled = enableButton()
            }

        })

        registerPasswordEditText.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                passwordValid = Pattern.compile(getString(R.string.regex_pass)).matcher(p0).matches()
                if (!passwordValid)
                    registerPasswordEditText.error = "Password is not valid"
                registerButton.isEnabled = enableButton()
            }

            override fun afterTextChanged(p0: Editable?) {
                passwordValid = Pattern.compile(getString(R.string.regex_pass)).matcher(p0).matches()
                if (!passwordValid)
                    registerPasswordEditText.error = "Password is not valid"
                registerButton.isEnabled = enableButton()
            }

        })
    }
}