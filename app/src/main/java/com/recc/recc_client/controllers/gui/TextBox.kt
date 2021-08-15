package com.recc.recc_client.controllers.gui

// Generic textbox that can be used for emails, passwords, etc.

import android.content.Context
import android.content.res.Resources
import android.widget.EditText
import com.recc.recc_client.MainActivity
import com.recc.recc_client.R
import com.recc.recc_client.contextFinder

enum class TextBoxType {
    TEXT,
    USERNAME,
    PASSWORD,
    EMAIL
}

open class Textbox(protected val value: String, protected val type: TextBoxType) {
    protected val context: Context = contextFinder.context
    protected var regex: Regex? = null
    init {
        println("Type: " + type + " | value: " + value)
        when (type) {
            TextBoxType.TEXT -> null
            TextBoxType.USERNAME -> this.regex = context.getString(R.string. textbox_username).toRegex()
            TextBoxType.PASSWORD -> this.regex = context.getString(R.string. textbox_pass).toRegex()
            TextBoxType.EMAIL -> this.regex = context.getString(R.string. textbox_email).toRegex()
        }
    }

    // If the given textbox differs from a text type then we use regex to validate it
    open fun is_valid(): Boolean = regex?.let {
        if (value.matches(it))
            return true
        return false
    } ?: true
}
