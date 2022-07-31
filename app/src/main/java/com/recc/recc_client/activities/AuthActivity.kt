package com.recc.recc_client.activities

import com.recc.recc_client.R
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.recc.recc_client.databinding.ActivityAuthBinding
import com.recc.recc_client.fragments.LoginFragment
import com.recc.recc_client.fragments.RegisterFragment

const val AUTH_MODE_REGISTER = 0
const val AUTH_MODE_LOGIN = 1

/**
 * Uses the same activity to either login or register by
 * switching a fragment inside a FragmentContainerView.
 */
class AuthActivity : AppCompatActivity() {
    private var authMode = AUTH_MODE_REGISTER

    // activity_auth layout binding
    private lateinit var binding: ActivityAuthBinding

    private val loginFragment: LoginFragment = LoginFragment()
    private val registerFragment: RegisterFragment = RegisterFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)
        updateAuthMode()
    }

    fun toggleAuthMode(ignoredParameter : View) {
        authMode = when (authMode) {
            AUTH_MODE_LOGIN -> AUTH_MODE_REGISTER
            AUTH_MODE_REGISTER -> AUTH_MODE_LOGIN
            else -> AUTH_MODE_LOGIN
        }
        updateAuthMode()
    }

    private fun updateAuthMode() {
        // binding.authModeFragmentContainer.removeAllViewsInLayout()

        val callToAction : Int
        val togglerDialogText : Int
        val togglerButtonText : Int
        val newFragment : Fragment

        if (authMode == AUTH_MODE_LOGIN) {
            callToAction = R.string.login_cta
            togglerDialogText = R.string.register_instead_cta
            togglerButtonText = R.string.register_cta
            newFragment = loginFragment
        } else {
            callToAction = R.string.register_cta
            togglerDialogText = R.string.login_instead_cta
            togglerButtonText = R.string.login_cta
            newFragment = registerFragment
        }

        binding.togglerCTA.setText(togglerDialogText)
        binding.authModeToggler.setText(togglerButtonText)
        // binding.authModeHeading.setText(callToAction)
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            // replace(binding.authModeFragmentContainer.id, newFragment)
        }
    }
}
