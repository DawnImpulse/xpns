package org.sourcei.xpns.activities

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import androidx.core.os.bundleOf
import kotlinx.android.synthetic.main.activity_category.*
import org.sourcei.xpns.R
import org.sourcei.xpns.fragments.CategoryFragment
import org.sourcei.xpns.utils.C
import org.sourcei.xpns.utils.ViewPagerAdapter

/**
 * @info -
 *
 * @author - Saksham
 * @note Last Branch Update - master
 *
 * @note Created on 2018-09-04 by Saksham
 * @note Updates :
 */
class CategoryActivity : AppCompatActivity() {
    private var select = false // whether to select or edit
    private lateinit var categoryFragment: CategoryFragment
    private lateinit var pagerAdapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        //select = intent.extras.getBoolean(C.SELECT)
        setupViewPager(categoryViewPager)
    }

    /**
     * Setup our viewpager
     */
    private fun setupViewPager(viewPager: ViewPager) {
        pagerAdapter = ViewPagerAdapter(supportFragmentManager)
        categoryFragment = CategoryFragment()

        var bundle = bundleOf(
                Pair(C.SELECT,true),
                Pair(C.TYPE,C.EXPENSE)
        )

        categoryFragment.arguments = bundle
        pagerAdapter.addFragment(categoryFragment, C.CATEGORY)
        viewPager.adapter = pagerAdapter
    }
}
