package com.recc.recc_client.activities

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity

// TODO: use a real source for this
const val IS_USER_LOGGED_IN = false

class LaunchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        print("Launching")

        if (IS_USER_LOGGED_IN) {
            throw Exception("TODO: build auth user logic.")
        } else {
            print("Launching auth activity")
            val authIntent = Intent(this, AuthActivity::class.java)
            startActivity(authIntent)
        }
    }
}
