package com.recc.recc_client.activities

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.recc.recc_client.api.clients.AuthApiClient

// TODO: use a real source for this
const val IS_USER_LOGGED_IN = false

class LaunchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?/*, persistentState: PersistableBundle?*/) {
        super.onCreate(savedInstanceState/*, persistentState*/)
        println("launching...")
        val api = AuthApiClient(applicationContext)
        //api.login(applicationContext, "pedro@gmail.com", "pedro")
        if (IS_USER_LOGGED_IN) {
            throw Exception("TODO: build auth user logic.")
        } else {
            println("Launching auth activity")
            val authIntent = Intent(this, AuthActivity::class.java)
            startActivity(authIntent)
        }
    }
}
