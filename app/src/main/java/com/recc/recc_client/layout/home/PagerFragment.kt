package com.recc.recc_client.layout.home

import android.os.Bundle
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.recc.recc_client.MainActivity
import com.recc.recc_client.R
import com.recc.recc_client.databinding.FragmentPagerBinding
import com.recc.recc_client.layout.common.BaseFragment
import com.recc.recc_client.layout.common.Event
import com.recc.recc_client.layout.common.MeDataViewModel
import com.recc.recc_client.layout.settings.SettingsFragment
import com.recc.recc_client.utils.Alert
import org.koin.androidx.viewmodel.ext.android.viewModel

class PagerFragment : BaseFragment<PagerScreenEvent, PagerViewModel, FragmentPagerBinding>(R.layout.fragment_pager) {
    override val viewModel: PagerViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity() as MainActivity).disableLoadingBar()
    }
    override fun subscribeToViewModel() {
        loadFragment(HomeFragment())

        // Changes the currently displayed Fragment on ViewPager whenever an item from Bottom Navigation View is selected
        binding.bnvHome.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_home -> {
                    loadFragment(HomeFragment())
                    true
                }
                R.id.action_settings -> {
                    loadFragment(SettingsFragment())
                    true
                }
                else -> false
            }
        }
        viewModel.screenEvent.observe(viewLifecycleOwner, Event.EventObserver { screenEvent ->
        })
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.fl_content, fragment)
        transaction.commit()
    }
}