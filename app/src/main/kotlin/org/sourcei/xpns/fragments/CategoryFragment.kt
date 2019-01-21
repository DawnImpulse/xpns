package org.sourcei.xpns.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
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
class CategoryFragment : androidx.fragment.app.Fragment() {
    private var select = false
    private var showChild = false
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
        showChild = arguments!!.getBoolean(C.SHOW_CHILD)
        type = arguments!!.getString(C.TYPE)!!

        model = ViewModelProviders.of(this, CategoryModelFactory(lifecycle, context!!)).get(CategoryModel::class.java)
        adapter = CategoryAdapter(lifecycle, select,showChild)

        model.getItems(type).observe(this, Observer {
            adapter.submitList(it)
        })
        categoryRecycler.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        categoryRecycler.adapter = adapter

    }

}
