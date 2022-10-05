package com.recc.recc_client

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.recc.recc_client.databinding.ActivityMainBinding
import com.recc.recc_client.utils.Status

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        Status( "Launching Main Activity...")
    }

    fun enableNoConnectionView() {
        binding.noConnectionView.show()
    }

    fun disableNoConnectionView() {
        binding.noConnectionView.hide()
    }

    fun enableLoadingBar() {
        binding.lbMain.visibility = View.VISIBLE
    }

    fun disableLoadingBar() {
        binding.lbMain.visibility = View.GONE
    }
}
