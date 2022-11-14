package com.recc.recc_client.layout.settings

import androidx.navigation.fragment.findNavController
import com.recc.recc_client.R
import com.recc.recc_client.databinding.FragmentSettingsBinding
import com.recc.recc_client.layout.common.BaseFragment
import com.recc.recc_client.layout.common.Event
import com.recc.recc_client.layout.common.MeDataViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : BaseFragment<SettingsScreenEvent, SettingsViewModel, FragmentSettingsBinding>(R.layout.fragment_settings) {
    override val viewModel: SettingsViewModel by viewModel()
    private val meDataViewModel: MeDataViewModel by viewModel()

    override fun subscribeToViewModel() {
        viewModel.screenEvent.observe(viewLifecycleOwner, Event.EventObserver { screenEvent ->
            when (screenEvent) {
                SettingsScreenEvent.onLoggedOut -> {
                    removeToken()
                    meDataViewModel.clear()
                    findNavController().navigate(R.id.action_pagerFragment_to_loginFragment)
                }
            }
        })
    }
}