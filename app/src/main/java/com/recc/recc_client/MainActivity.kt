package com.recc.recc_client

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.recc.recc_client.controllers.ContextFinder
import com.recc.recc_client.screens.LoginActivity
import com.recc.recc_client.screens.RegisterActivity
import kotlinx.android.synthetic.main.login_main.*

var contextFinder = ContextFinder()

class MainActivity : AppCompatActivity() {
    companion object {
        lateinit var context: Context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_main)
        context = applicationContext
        println(contextFinder.context)

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        // TODO: Cleanup
    }
}