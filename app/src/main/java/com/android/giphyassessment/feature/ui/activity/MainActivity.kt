package com.android.giphyassessment.feature.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.giphyassessment.databinding.ActivityMainBinding
import com.android.giphyassessment.feature.shared.adapters.TabLayoutAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setup()
    }

    private fun setup() {
        binding.tblLayout.addTab(binding.tblLayout.newTab().setText("Giphy"));
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
        })
    }

}