package com.recc.recc_client.layout.welcome

import android.os.Bundle
import com.recc.recc_client.MainActivity
import com.recc.recc_client.R
import com.recc.recc_client.databinding.FragmentWelcomeBinding
import com.recc.recc_client.layout.common.BaseFragment
import com.recc.recc_client.utils.Alert
import org.koin.androidx.viewmodel.ext.android.viewModel

class WelcomeFragment : BaseFragment<WelcomeScreenEvent, WelcomeViewModel, FragmentWelcomeBinding>(R.layout.fragment_welcome) {
    override val viewModel: WelcomeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity() as MainActivity).disableLoadingBar()
    }

    override fun subscribeToViewModel() {
        Alert("calling viewModel")
        viewModel.getTopArtists()
    }
}