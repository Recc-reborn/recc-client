package com.recc.recc_client.utils

import android.content.Context
import com.recc.recc_client.R
import kotlin.text.Regex

enum class RegexType(val type: String) {
    EMAIL("email"),
    PASSWORD("password"),
    USERNAME("username"),
    RAW("raw")
}

object Regex {
    operator fun invoke(context: Context, type: String): Regex {
        return when (type) {
            RegexType.EMAIL.type -> context.getString(R.string.regex_email).toRegex()
            RegexType.PASSWORD.type -> context.getString(R.string.regex_pass).toRegex()
            RegexType.USERNAME.type -> context.getString(R.string.regex_username).toRegex()
            RegexType.RAW.type -> ".".toRegex()
            else -> throw IllegalArgumentException("$type type argument isn't valid")
        }
    }
}