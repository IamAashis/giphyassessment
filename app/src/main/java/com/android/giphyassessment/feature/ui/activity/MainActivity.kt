package com.android.giphyassessment.feature.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.giphyassessment.R
import com.android.giphyassessment.databinding.ActivityMainBinding
import com.android.giphyassessment.feature.shared.adapters.TabLayoutAdapter
import com.android.giphyassessment.feature.ui.fragment.favourite.FavouriteFragment
import com.android.giphyassessment.feature.ui.fragment.giphy.GiphyFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    val tabAdapter = TabLayoutAdapter(supportFragmentManager)
    var giphyFragment = GiphyFragment()
    var favouriteFragment = FavouriteFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setup()
    }

    private fun setup() {
        setUpView()
        binding.tblLayout.setupWithViewPager(binding.vewPager)
        setTabIcons()
        initTabLayout()
        /*     binding.tblLayout.addTab(binding.tblLayout.newTab().setText("Giphy"));
             binding.tblLayout.addTab(binding.tblLayout.newTab().setText("Favourite"));


             val adapter = TabLayoutAdapter(this, supportFragmentManager, binding.tblLayout.tabCount)
             binding.vewPager.adapter = adapter
             binding.vewPager.addOnPageChangeListener(TabLayoutOnPageChangeListener(binding.tblLayout))

             binding.tblLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
                 override fun onTabSelected(tab: TabLayout.Tab) {
                     binding.vewPager.setCurrentItem(tab.position)
                 }

                 override fun onTabUnselected(tab: TabLayout.Tab) {}
                 override fun onTabReselected(tab: TabLayout.Tab) {}
             })*/
    }

    private fun setTabIcons() {
        binding.tblLayout.getTabAt(0)?.setIcon(R.drawable.gif_box)
        binding.tblLayout.getTabAt(1)?.setIcon(R.drawable.ic_favorite)
    }

    private fun setUpView() {
        tabAdapter.addFragment(
            giphyFragment,
            getString(R.string.gifsTrending),
            getString(R.string.gifsTrending)
        )
        tabAdapter.addFragment(
            favouriteFragment,
            getString(R.string.favourite),
            getString(R.string.favourite)
        )
//        binding.vewPager.offscreenPageLimit = 1

        binding.vewPager.adapter = tabAdapter
    }

    private fun initTabLayout() {
        binding.tblLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        binding.tblLayout.getTabAt(0)?.setIcon(R.drawable.gif_box)
                        val fragment = tabAdapter.getItem(0)
                        if (fragment is GiphyFragment) {
                            fragment.getTrendingData()
                        }
                    }

                    1 -> {
                        binding.tblLayout.getTabAt(1)?.setIcon(R.drawable.ic_favorite)
                        val fragment = tabAdapter.getItem(1)
                        if (fragment is FavouriteFragment) {
                            fragment.getFavData()
                        }
                    }

                }
            }
        })
    }

}