package org.sourcei.xpns.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * @info -
 *
 * @author - Saksham
 * @tnote Last Branch Update - master
 *
 * @tnote Created on 2018-08-19 by Saksham
 * @tnote Updates :
 */
class ViewPagerAdapter(fm: androidx.fragment.app.FragmentManager) : androidx.fragment.app.FragmentPagerAdapter(fm) {
    private val mFragmentList = ArrayList<androidx.fragment.app.Fragment>()
    private val mFragmentTitleList = ArrayList<String>()

    /**
     * Get item at position
     */
    override fun getItem(position: Int): androidx.fragment.app.Fragment {
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
    internal fun addFragment(fragment: androidx.fragment.app.Fragment, title: String) {
        mFragmentList.add(fragment)
        mFragmentTitleList.add(title)
    }
}