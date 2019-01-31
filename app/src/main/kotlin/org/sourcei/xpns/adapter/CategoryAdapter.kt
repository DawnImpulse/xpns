package org.sourcei.xpns.adapter

import android.app.Activity
import android.content.Context
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import org.sourcei.xpns.pojo.CategoryPojo
import org.sourcei.xpns.viewholders.CategoryViewHolder

/**
 * @info -
 *
 * @author - Saksham
 * @tnote Last Branch Update - master
 *
 * @tnote Created on 2018-09-04 by Saksham
 * @tnote Updates :
 */
class CategoryAdapter(
    private val lifecycle: Lifecycle,
    private val select: Boolean,
    private val items: List<CategoryPojo>,
    private val showChild: Boolean = true
) : RecyclerView.Adapter<CategoryViewHolder>() {

    private val NAME = " CategoryAdapter"
    private lateinit var context: Context

    // no of items
    override fun getItemCount(): Int {
        return items.size
    }

    // on create view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        context = parent.context
        return CategoryViewHolder(parent, lifecycle, select)
    }

    // bind view
    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        (context as Activity).runOnUiThread {
            holder.bindTo(items[position], showChild)
        }
    }

}