package com.android.giphyassessment.feature.shared.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * Created by Aashis on 05,September,2023
 */

class MyFragmentPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        // Return the total number of tabs
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        // Return the fragment for the corresponding position
        return when (position) {
            0 -> Fragment1()
            1 -> Fragment2()
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
    }
}