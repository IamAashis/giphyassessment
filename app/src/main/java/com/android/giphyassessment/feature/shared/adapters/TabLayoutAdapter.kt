package com.android.giphyassessment.feature.shared.adapters

import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter


/**
 * Created by Aashis on 05,September,2023
 */

class TabLayoutAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    val mFragmentList = ArrayList<Fragment>()
    private val mFragmentTitleList = ArrayList<String>()
    private val mFragmentTagList = ArrayList<String>()

    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]
    }

    fun addFragment(fragment: Fragment, title: String, fragmentTag: String) {
        mFragmentList.add(fragment)
        mFragmentTitleList.add(title)
        mFragmentTagList.add(fragmentTag)
    }

    @Nullable
    override fun getPageTitle(position: Int): CharSequence? {
        return mFragmentTitleList[position]
    }

    override fun getCount(): Int {
        return mFragmentList.size
    }

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }
}
