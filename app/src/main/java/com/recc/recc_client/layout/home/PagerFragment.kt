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

private val fragmentPages = listOf<Class<*>>(
    HomeFragment::class.java,
    SettingsFragment::class.java
)

class PagerFragment : BaseFragment<PagerScreenEvent, PagerViewModel, FragmentPagerBinding>(R.layout.fragment_pager) {
    override val viewModel: PagerViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity() as MainActivity).disableLoadingBar()
    }

    override fun subscribeToViewModel() {
        // Sets a new adapter for ViewPager
        binding.vpHome.adapter = PageAdapter(this)
        // Changes Bottom Navigation View's selected item whenever we scroll to another page using the ViewPager.
        binding.vpHome.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.bnvHome.menu[position].isChecked = true
            }
        })
        // Changes the currently displayed Fragment on ViewPager whenever an item from Bottom Navigation View is selected
        binding.bnvHome.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_home -> {
                    binding.vpHome.currentItem = fragmentPages.indexOf(HomeFragment::class.java)
                    true
                }
                R.id.action_settings -> {
                    binding.vpHome.currentItem = fragmentPages.indexOf(SettingsFragment::class.java)
                    true
                }
                else -> false
            }
        }
        viewModel.screenEvent.observe(viewLifecycleOwner, Event.EventObserver { screenEvent ->
        })
    }

    inner class PageAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int = fragmentPages.count()

        override fun createFragment(position: Int): Fragment = fragmentPages[position].constructors.first().newInstance() as Fragment
    }
}