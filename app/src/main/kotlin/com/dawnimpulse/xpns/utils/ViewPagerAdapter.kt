package com.dawnimpulse.xpns.utils

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * @info -
 *
 * @author - Saksham
 * @note Last Branch Update - master
 *
 * @note Created on 2018-08-19 by Saksham
 * @note Updates :
 */
class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    private val mFragmentList = ArrayList<Fragment>()
    private val mFragmentTitleList = ArrayList<String>()

    /**
     * Get item at position
     */
    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]
    }

    /**
     * Get total count of fragments
     */
    override fun getCount(): Int {
        return mFragmentList.size
    }

    /**
     * Get page title
     */
    override fun getPageTitle(position: Int): CharSequence? {
        return mFragmentTitleList[position]
    }

    /**
     * Add fragment to the list
     */
    internal fun addFragment(fragment: Fragment, title: String) {
        mFragmentList.add(fragment)
        mFragmentTitleList.add(title)
    }
}