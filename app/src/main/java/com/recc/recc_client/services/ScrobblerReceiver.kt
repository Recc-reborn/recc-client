package com.recc.recc_client.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.recc.recc_client.utils.Alert

class ScrobblerReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Alert("intent")
        if (intent == null) {
            Alert("intent is null")
            return
        }
        val timeSentInMs = intent.getLongExtra("timeSent", 0L)
        val action = intent.action
        Alert("action: $action")
    }
}