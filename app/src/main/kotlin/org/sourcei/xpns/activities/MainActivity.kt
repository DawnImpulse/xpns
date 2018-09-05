package org.sourcei.xpns.activities

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import it.sephiroth.android.library.bottomnavigation.BottomNavigation
import kotlinx.android.synthetic.main.activity_main.*
import org.sourcei.xpns.R
import org.sourcei.xpns.fragments.TransactionsFragment
import org.sourcei.xpns.utils.C
import org.sourcei.xpns.utils.ViewPagerAdapter

/**
 * @info -
 *
 * @author - Saksham
 * @note Last Branch Update - master
 *
 * @note Created on 2018-08-12 by Saksham
 * @note Updates :
 */
class MainActivity : AppCompatActivity(), BottomNavigation.OnMenuItemSelectionListener, ViewPager.OnPageChangeListener {
    private var lastItemSelected = 0
    private lateinit var pagerAdapter: ViewPagerAdapter
    private lateinit var transactionsFragment: TransactionsFragment
    /**
     * On create
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViewPager(mainViewPager)
        navigation.setOnMenuItemClickListener(this)
        mainViewPager.addOnPageChangeListener(this)
        mainViewPager.offscreenPageLimit = 2
    }

    /**
     * On menu item select
     */
    override fun onMenuItemSelect(p0: Int, position: Int, p2: Boolean) {
        if (position == 2) {
            startActivity(Intent(this,AddTransaction::class.java))
        } else if (position == 4) {
            //
        } else {
            lastItemSelected = position
            mainViewPager.currentItem = position
        }
    }

    /**
     * On menu item reselect
     */
    override fun onMenuItemReselect(p0: Int, p1: Int, p2: Boolean) {

    }

    /**
     * Setup our viewpager
     */
    private fun setupViewPager(viewPager: ViewPager) {
        pagerAdapter = ViewPagerAdapter(supportFragmentManager)
        transactionsFragment = TransactionsFragment()

        pagerAdapter.addFragment(transactionsFragment, C.TRANSACTIONS)
        viewPager.adapter = pagerAdapter
    }


    /**
     * On page selected (viewpager)
     */
    override fun onPageSelected(position: Int) {
        navigation.setSelectedIndex(position, true)
    }

    /**
     * On page scroll state (viewpager)
     */
    override fun onPageScrollStateChanged(state: Int) {
        //
    }

    /**
     * On page scrolled (viewpager)
     */
    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        //
    }
}
