package org.sourcei.xpns.activities

import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import org.sourcei.xpns.R
import org.sourcei.xpns.fragments.TransactionsFragment
import org.sourcei.xpns.sheets.ModalSheetNav
import org.sourcei.xpns.utils.C
import org.sourcei.xpns.utils.Colors
import org.sourcei.xpns.utils.ViewPagerAdapter

/**
 * @info -
 *
 * @author - Saksham
 * @tnote Last Branch Update - master
 *
 * @tnote Created on 2018-08-12 by Saksham
 * @tnote Updates :
 * Saksham - 2018 09 13 - master - custom bottom nav
 */
class MainActivity : AppCompatActivity(), ViewPager.OnPageChangeListener, View.OnClickListener {
    private var lastItemSelected = 0
    private lateinit var pagerAdapter: ViewPagerAdapter
    private lateinit var transactionsFragment: TransactionsFragment
    private lateinit var nav: ModalSheetNav

    // On create
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nav = ModalSheetNav()
        setupViewPager(mainViewPager)
        mainViewPager.addOnPageChangeListener(this)
        mainViewPager.offscreenPageLimit = 2
        changeNavColor(0)

        mainNavAdd.setOnClickListener(this)
        mainNavUp.setOnClickListener(this)
    }

    // On page selected (viewpager)
    override fun onPageSelected(position: Int) {
        changeNavColor(position)
    }

    //
    override fun onPageScrollStateChanged(state: Int) {
        //
    }

    //
    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        //
    }

    // on click
    override fun onClick(v: View) {
        when (v.id) {
            mainNavAdd.id -> startActivity(Intent(this, AddTransaction::class.java))
            mainNavUp.id -> nav.show(supportFragmentManager,nav.tag)
        }
    }

    // Setup our viewpager
    private fun setupViewPager(viewPager: ViewPager) {
        pagerAdapter = ViewPagerAdapter(supportFragmentManager)
        transactionsFragment = TransactionsFragment()

        pagerAdapter.addFragment(transactionsFragment, C.TRANSACTIONS)
        viewPager.adapter = pagerAdapter
    }

    // change navigation color
    private fun changeNavColor(pos: Int) {
        val colors = Colors(this)
        mainNavUpI.drawable.setColorFilter(colors.GREY_400, PorterDuff.Mode.SRC_ATOP)
        when (pos) {
            0 -> {
                mainNavHomeI.drawable.setColorFilter(colors.BLACK, PorterDuff.Mode.SRC_ATOP)
                mainNavStatI.drawable.setColorFilter(colors.GREY_400, PorterDuff.Mode.SRC_ATOP)
                mainNavHistoryI.drawable.setColorFilter(colors.GREY_400, PorterDuff.Mode.SRC_ATOP)
            }
            1 -> {
                mainNavHomeI.drawable.setColorFilter(colors.GREY_400, PorterDuff.Mode.SRC_ATOP)
                mainNavStatI.drawable.setColorFilter(colors.BLACK, PorterDuff.Mode.SRC_ATOP)
                mainNavHistoryI.drawable.setColorFilter(colors.GREY_400, PorterDuff.Mode.SRC_ATOP)
            }
            2 -> {
                mainNavHomeI.drawable.setColorFilter(colors.GREY_400, PorterDuff.Mode.SRC_ATOP)
                mainNavStatI.drawable.setColorFilter(colors.GREY_400, PorterDuff.Mode.SRC_ATOP)
                mainNavHistoryI.drawable.setColorFilter(colors.GREY_400, PorterDuff.Mode.SRC_ATOP)
            }
            3 -> {
                mainNavStatI.drawable.setColorFilter(colors.GREY_400, PorterDuff.Mode.SRC_ATOP)
                mainNavHomeI.drawable.setColorFilter(colors.GREY_400, PorterDuff.Mode.SRC_ATOP)
                mainNavHistoryI.drawable.setColorFilter(colors.BLACK, PorterDuff.Mode.SRC_ATOP)
            }
            4 -> {
                mainNavHomeI.drawable.setColorFilter(colors.GREY_400, PorterDuff.Mode.SRC_ATOP)
                mainNavStatI.drawable.setColorFilter(colors.GREY_400, PorterDuff.Mode.SRC_ATOP)
                mainNavHistoryI.drawable.setColorFilter(colors.GREY_400, PorterDuff.Mode.SRC_ATOP)
            }
        }
    }
}
