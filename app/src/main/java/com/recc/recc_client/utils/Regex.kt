package com.recc.recc_client.utils

import android.content.Context
import com.recc.recc_client.R
import kotlin.text.Regex

const val EMAIL_TYPE = "email"
const val PASSWORD_TYPE = "password"
const val USERNAME_TYPE = "username"

object Regex {
    operator fun invoke(context: Context, type: String): Regex {
        return when (type) {
            EMAIL_TYPE -> context.getString(R.string.regex_email).toRegex()
            PASSWORD_TYPE -> context.getString(R.string.regex_pass).toRegex()
            USERNAME_TYPE -> context.getString(R.string.regex_username).toRegex()
            else -> ".".toRegex()
        }
    }
}