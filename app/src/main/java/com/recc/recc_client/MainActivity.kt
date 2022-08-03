package com.recc.recc_client

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.recc.recc_client.utils.L

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        L.status( "Launching Main Activity...")
    }
}
