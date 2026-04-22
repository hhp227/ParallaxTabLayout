package com.hhp227.parallaxlayout.fragment.tab

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.hhp227.parallaxlayout.R
import com.hhp227.parallaxlayout.databinding.FragmentParallaxTabBinding
import kotlin.collections.get

class ParallaxTabFragment : Fragment() {
    private val fragmentList by lazy {
        arrayListOf(
            FirstTabFragment.newInstance(arguments?.getString("group") ?: ""),
            SecondTabFragment.newInstance(arguments?.getString("group") ?: "")
        )
    }

    private lateinit var binding: FragmentParallaxTabBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentParallaxTabBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setNavAppBar(binding.toolbar)
        binding.viewPager.apply {
            adapter = object : FragmentStateAdapter(this@ParallaxTabFragment) {
                override fun getItemCount() = fragmentList.size

                override fun createFragment(position: Int) = fragmentList[position]
            }
            offscreenPageLimit = fragmentList.size
        }
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = resources.getStringArray(R.array.tab_name)[position]
        }.attach()
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                binding.isTabPositionZero = tab?.position == 0

                when (val fragment = fragmentList[tab?.position ?: 0]) {
                    is FirstTabFragment -> {
                        if (!fragment.isFirstItemVisible()) {
                            setAppbarLayoutExpand(false)
                        }
                    }
                    is SecondTabFragment -> {
                        if (!fragment.isFirstItemVisible()) {
                            setAppbarLayoutExpand(false)
                        }
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) = Unit

            override fun onTabReselected(tab: TabLayout.Tab?) = Unit
        })
    }

    private fun setNavAppBar(toolbar: Toolbar) {
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        binding.collapsingToolbar.setupWithNavController(toolbar, findNavController())
    }

    fun setAppbarLayoutExpand(isExpanded: Boolean) {
        binding.appBarLayout.setExpanded(isExpanded)
    }
}