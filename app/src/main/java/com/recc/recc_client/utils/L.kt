package com.recc.recc_client.utils

import android.util.Log

object L {
    /**
     * @see android.util.Log.d
     */
    fun d(tag: String?, msg: String?) {
        Log.d(tag, msg!!)
    }

    /**
     * Log.d using "ALERT" as TAG
     * @see android.util.Log.d
     */
    fun alert(msg: String?) {
        d("ALERT", msg)
    }

    /**
     * Log.d using "STATUS" as TAG
     * @see android.util.Log.d
     */
    fun status(msg: String?) {
        d("STATUS", msg)
    }

    /**
     * Log.d using "TEST" as TAG
     * @see android.util.Log.d
     */
    fun test(msg: String?) {
        d("TEST", msg)
    }
}