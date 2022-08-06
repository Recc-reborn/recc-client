package com.recc.recc_client.utils

import android.content.Context

fun Float.toDp(context: Context): Int = (context.resources.displayMetrics.density * this).toInt()