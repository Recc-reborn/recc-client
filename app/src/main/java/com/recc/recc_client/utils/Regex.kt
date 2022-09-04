package com.recc.recc_client.utils

import android.content.Context
import com.recc.recc_client.R
import kotlin.text.Regex

enum class RegexType(val type: String) {
    EMAIL("email"),
    PASSWORD("password"),
    USERNAME("username")
}

object Regex {
    operator fun invoke(context: Context, type: RegexType): Regex {
        return when (type) {
            RegexType.EMAIL -> context.getString(R.string.regex_email).toRegex()
            RegexType.PASSWORD -> context.getString(R.string.regex_pass).toRegex()
            RegexType.USERNAME -> context.getString(R.string.regex_username).toRegex()
            else -> ".".toRegex()
        }
    }
}