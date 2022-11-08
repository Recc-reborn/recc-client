package com.recc.recc_client.layout.home

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.recc.recc_client.MainActivity
import com.recc.recc_client.R
import com.recc.recc_client.databinding.FragmentHomeBinding
import com.recc.recc_client.layout.common.BaseFragment
import com.recc.recc_client.layout.common.Event
import com.recc.recc_client.layout.common.MeDataViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<HomeScreenEvent, HomeViewModel, FragmentHomeBinding>(R.layout.fragment_home) {
    override val viewModel: HomeViewModel by viewModel()
    private val meDataViewModel: MeDataViewModel by viewModel()

//    init {
//        viewModel.meData.value?.let {
//            if (!it.hasSetPreferredArtists) {
//                findNavController().navigate(R.id.action_homeFragment_to_welcomeFragment)
//            }
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity() as MainActivity).disableLoadingBar()
    }

    override fun subscribeToViewModel() {
        viewModel.screenEvent.observe(viewLifecycleOwner, Event.EventObserver { screenEvent ->
            when (screenEvent) {
                HomeScreenEvent.onLoggedOut -> {
                    removeToken()
                    meDataViewModel.clear()
                    findNavController().navigate(R.id.action_back_to_login)
                }
            }
        })
    }
}