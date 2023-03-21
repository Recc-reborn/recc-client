package com.recc.recc_client.layout.settings

import androidx.navigation.fragment.findNavController
import com.recc.recc_client.MainActivity
import com.recc.recc_client.R
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

    override fun subscribeToViewModel() {
        Alert("spotify satatus fragment: ${sharedPreferences.getSpotifyStatus()}")
        if (sharedPreferences.getSpotifyStatus()) {
            binding.btnConnectSpotify.text = getString(R.string.cta_log_out_from_spotify)
        } else {
            binding.btnConnectSpotify.text = getString(R.string.cta_connect_to_spotify)
        }

        binding.btnConnectSpotify.setOnClickListener {
            viewModel.handleSpotifyBtn(requireActivity() as MainActivity)
        }

        binding.btnLogout.setOnClickListener {
            viewModel.onBtnLogout(requireActivity() as MainActivity)
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