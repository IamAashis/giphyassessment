package com.android.giphyassessment.feature.shared.adapters

import android.content.Context
import android.util.Log
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
import androidx.fragment.app.FragmentStatePagerAdapter
import com.android.giphyassessment.feature.ui.fragment.favourite.FavouriteFragment
import com.android.giphyassessment.feature.ui.fragment.giphy.GiphyFragment


/**
 * Created by Aashis on 05,September,2023
 */

/*class TabLayoutAdapter(context: Context, fragmentManager: FragmentManager, totalTabs: Int) :
    FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val mContext: Context = context
    private val mTotalTabs: Int = totalTabs

    override fun getItem(position: Int): Fragment {
        Log.d("asasas", position.toString())
        return when (position) {
            0 -> GiphyFragment()
            1 -> FavouriteFragment()
            else -> throw IllegalArgumentException("Invalid tab position: $position")
        }
    }

    override fun getCount(): Int {
        return mTotalTabs
    }
}*/

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
