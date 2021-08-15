package com.recc.recc_client.controllers

import android.content.Context
import com.recc.recc_client.MainActivity

data class ContextFinder(
    var context: Context = MainActivity.context
)