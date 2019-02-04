package org.sourcei.xpns.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import kotlinx.android.synthetic.main.activity_category.*
import org.sourcei.xpns.R
import org.sourcei.xpns.fragments.CategoryFragment
import org.sourcei.xpns.utils.C
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
 */
class CategoryActivity : AppCompatActivity(), View.OnClickListener {

    private var select = false // whether to select or edit
    private var showChild = false // if we need to show only parents
    private var fab = false //to show fab or not
    private lateinit var categoryFragment: CategoryFragment
    private lateinit var pagerAdapter: ViewPagerAdapter

    // on create
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        select = intent.extras!!.getBoolean(C.SELECT)
        showChild = intent.extras!!.getBoolean(C.SHOW_CHILD)
        fab = intent.extras!!.getBoolean(C.FAB)
        setupViewPager(categoryViewPager)
        categoryFab.setOnClickListener(this)

        if (!fab)
            categoryFab.gone()
    }

    // on click
    override fun onClick(v: View) {
        when (v.id) {
            categoryFab.id -> startActivity(Intent(this, AddCategoryActivity::class.java))
        }
    }

    // Setup our viewpager
    private fun setupViewPager(viewPager: androidx.viewpager.widget.ViewPager) {
        pagerAdapter = ViewPagerAdapter(supportFragmentManager)
        categoryFragment = CategoryFragment()

        val bundle = bundleOf(
            Pair(C.SELECT, select),
            Pair(C.TYPE, C.EXPENSE),
            Pair(C.SHOW_CHILD, showChild)
        )

        categoryFragment.arguments = bundle
        pagerAdapter.addFragment(categoryFragment, C.CATEGORY)
        viewPager.adapter = pagerAdapter
    }

    // back pressed
    override fun onBackPressed() {
        setResult(Activity.RESULT_CANCELED)
        finish()
    }
}
