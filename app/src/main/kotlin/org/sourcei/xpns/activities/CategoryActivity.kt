package org.sourcei.xpns.activities

import android.app.Activity
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_category.*
import org.sourcei.xpns.R
import org.sourcei.xpns.fragments.CategoryFragment
import org.sourcei.xpns.utils.C
import org.sourcei.xpns.utils.Colors
import org.sourcei.xpns.utils.ViewPagerAdapter
import org.sourcei.xpns.utils.gone

/**
 * @info -
 *
 * @author - Saksham
 * @note Last Branch Update - master
 *
 * @note Created on 2018-09-04 by Saksham
 * @note Updates :
 *  Saksham - 2019 02 09 - master - saving fragment handling
 */
class CategoryActivity : AppCompatActivity(), View.OnClickListener {

    private var select = false // whether to select(true) or edit(false)
    private var showChild = false // if we need to show only parents
    private var fab = false //to show fab or not
    private lateinit var expenseFragment: CategoryFragment
    private lateinit var savingFragment: CategoryFragment
    private lateinit var pagerAdapter: ViewPagerAdapter

    // on create
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        select = intent.extras!!.getBoolean(C.SELECT)
        showChild = intent.extras!!.getBoolean(C.SHOW_CHILD)
        fab = intent.extras!!.getBoolean(C.FAB)
        setupViewPager(categoryViewPager)
        selectType(0)

        categoryFab.setOnClickListener(this)
        categoryExpense.setOnClickListener(this)
        categorySaving.setOnClickListener(this)

        if (!fab)
            categoryFab.gone()
    }

    // on click
    override fun onClick(v: View) {
        when (v.id) {
            categoryFab.id -> startActivity(Intent(this, AddCategoryActivity::class.java))
            categoryExpense.id -> selectType(0)
            categorySaving.id -> selectType(1)
        }
    }

    // back pressed
    override fun onBackPressed() {
        if (select)
            setResult(Activity.RESULT_CANCELED)
        finish()
    }

    // Setup our viewpager
    private fun setupViewPager(viewPager: androidx.viewpager.widget.ViewPager) {
        pagerAdapter = ViewPagerAdapter(supportFragmentManager)
        expenseFragment = CategoryFragment()
        savingFragment = CategoryFragment()

        val expenseBundle = bundleOf(
            Pair(C.SELECT, select),
            Pair(C.TYPE, C.EXPENSE),
            Pair(C.SHOW_CHILD, showChild)
        )

        val savingBundle = bundleOf(
            Pair(C.SELECT, select),
            Pair(C.TYPE, C.SAVING),
            Pair(C.SHOW_CHILD, showChild)
        )

        expenseFragment.arguments = expenseBundle
        savingFragment.arguments = savingBundle

        pagerAdapter.addFragment(expenseFragment, C.EXPENSE)
        pagerAdapter.addFragment(savingFragment, C.SAVING)
        viewPager.adapter = pagerAdapter

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
                //
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                //
            }

            override fun onPageSelected(position: Int) {
                selectType(position)
            }

        })
    }

    // select type
    private fun selectType(pos: Int) {
        val black = Colors(this).BLACK
        val grey = Colors(this).GREY_500

        when (pos) {
            0 -> {
                categoryExpenseT.setTextColor(black)
                categorySavingT.setTextColor(grey)
                categoryExpenseT.setTypeface(categoryExpenseT.typeface, Typeface.BOLD)
                categorySavingT.setTypeface(categoryExpenseT.typeface, Typeface.NORMAL)
                categoryViewPager.currentItem = 0
            }
            1 -> {
                categoryExpenseT.setTextColor(grey)
                categorySavingT.setTextColor(black)
                categoryExpenseT.setTypeface(categoryExpenseT.typeface, Typeface.NORMAL)
                categorySavingT.setTypeface(categoryExpenseT.typeface, Typeface.BOLD)
                categoryViewPager.currentItem = 1
            }
        }
    }
}
