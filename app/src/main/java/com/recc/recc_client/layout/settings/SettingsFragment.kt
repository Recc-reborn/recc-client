package com.recc.recc_client.layout.settings

import android.app.Activity
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.recc.recc_client.MainActivity
import com.recc.recc_client.R
import com.recc.recc_client.WebViewActivity
import com.recc.recc_client.databinding.FragmentSettingsBinding
import com.recc.recc_client.layout.common.BaseFragment
import com.recc.recc_client.layout.common.Event
import com.recc.recc_client.layout.common.MeDataViewModel
import com.recc.recc_client.utils.Alert
import com.recc.recc_client.utils.SharedPreferences
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : BaseFragment<SettingsScreenEvent, SettingsViewModel, FragmentSettingsBinding>(R.layout.fragment_settings) {
    override val viewModel: SettingsViewModel by viewModel()
    private val meDataViewModel: MeDataViewModel by viewModel()
    private val sharedPreferences: SharedPreferences by inject()
    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result?.resultCode == Activity.RESULT_OK) {
            result.data?.apply {
                val code = getStringExtra("code")
                val codeVerifier = getStringExtra("codeVerifier")
                val codeChallenge = getStringExtra("codeChallenge")
                (requireActivity() as MainActivity).loginToSpotify(code.orEmpty(), codeVerifier.orEmpty(), codeChallenge.orEmpty())
            }
        }
    }

    override fun subscribeToViewModel() {
        Alert("spotify status fragment: ${sharedPreferences.getSpotifyStatus()}")
        if (sharedPreferences.getSpotifyStatus()) {
            binding.btnConnectSpotify.text = getString(R.string.cta_log_out_from_spotify)
        } else {
            binding.btnConnectSpotify.text = getString(R.string.cta_connect_to_spotify)
        }

        binding.btnConnectSpotify.setOnClickListener {
            val intent = Intent(requireContext(), WebViewActivity::class.java)
            resultLauncher.launch(intent)
        }

        viewModel.screenEvent.observe(viewLifecycleOwner, Event.EventObserver { screenEvent ->
            when (screenEvent) {
                SettingsScreenEvent.OnLoggedOut -> {
                    sharedPreferences.removeToken()
                    meDataViewModel.clear()
                    findNavController().navigate(R.id.action_pagerFragment_to_loginFragment)
                } SettingsScreenEvent.SetLoginSpotifyBtn -> {
                    binding.btnConnectSpotify.text = getString(R.string.cta_log_out_from_spotify)
                } SettingsScreenEvent.SetLogoutSpotifyBtn -> {
                    binding.btnConnectSpotify.text = getString(R.string.cta_connect_to_spotify)
                }
            }
        })
    }
}