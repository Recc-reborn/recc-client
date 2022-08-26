package com.recc.recc_client.layout.home

import android.content.Context
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.recc.recc_client.R
import com.recc.recc_client.databinding.FragmentHomeBinding
import com.recc.recc_client.layout.common.BaseFragment
import com.recc.recc_client.layout.common.Event
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<HomeScreenEvent, HomeViewModel, FragmentHomeBinding>(R.layout.fragment_home) {
    override val viewModel: HomeViewModel by viewModel()

    init {
        viewModel.meData.value?.let {
            if (!it.hasSetPreferredArtists) {
                findNavController().navigate(R.id.action_homeFragment_to_welcomeFragment)
            }
        }
    }

    private fun removeToken() {
        val keyFile = getString(R.string.preference_auth_key_file)
        val sharedPref = requireActivity().getSharedPreferences(keyFile, Context.MODE_PRIVATE)
        with (sharedPref?.edit()) {
            this?.remove(getString(R.string.auth_token_key))
            this?.apply()
        }
    }

    override fun subscribeToViewModel() {
        viewModel.screenEvent.observe(viewLifecycleOwner, Event.EventObserver { screenEvent ->
            when (screenEvent) {
                HomeScreenEvent.onLoggedOut -> {
                    removeToken()
                    Toast.makeText(requireContext(), "See you soon!", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
                }
            }
        })
    }
}