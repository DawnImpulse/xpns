package org.sourcei.xpns.fragments


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_category.*
import org.sourcei.xpns.R
import org.sourcei.xpns.adapter.CategoryAdapter
import org.sourcei.xpns.models.CategoryModel
import org.sourcei.xpns.models.CategoryModelFactory
import org.sourcei.xpns.utils.C


/**
 * @info -
 *
 * @author - Saksham
 * @tnote Last Branch Update - master
 *
 * @tnote Created on 2018-09-04 by Saksham
 * @tnote Updates :
 */
class CategoryFragment : Fragment() {
    private var select = false
    private lateinit var model: CategoryModel
    private lateinit var adapter: CategoryAdapter
    private lateinit var type: String

    // on create
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    // on view created
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        select = arguments!!.getBoolean(C.SELECT)
        type = arguments!!.getString(C.TYPE)

        model = ViewModelProviders.of(this, CategoryModelFactory(lifecycle, context!!)).get(CategoryModel::class.java)
        adapter = CategoryAdapter(lifecycle, select)
        model.getItems(type).observe(this, Observer {
            adapter.submitList(it)
        })
        categoryRecycler.layoutManager = LinearLayoutManager(context)
        categoryRecycler.adapter = adapter

    }

}
