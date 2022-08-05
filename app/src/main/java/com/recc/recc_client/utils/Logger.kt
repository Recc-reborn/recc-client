package com.recc.recc_client.utils

import android.util.Log

/**
 * Log.d using "ALERT" as TAG
 * @see android.util.Log.d
 */
object Alert {
    operator fun invoke(msg: String?) {
        Log.d("ALERT", msg!!)
    }
}

/**
 * Log.d using "STATUS" as TAG
 * @see android.util.Log.d
 */
object Status {
    operator fun invoke(msg: String?) {
        Log.d("STATUS", msg!!)
    }
}

/**
 * Log.d using "TEST" as TAG
 * @see android.util.Log.d
 */
object Test {
    operator fun invoke(msg: String?) {
        Log.d("TEST", msg!!)
    }
}