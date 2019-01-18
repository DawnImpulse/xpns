package org.sourcei.xpns.adapter

import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import org.sourcei.xpns.models.CategoryModel
import org.sourcei.xpns.viewholders.ChildCategoryViewHolder

/**
 * @info -
 *
 * @author - Saksham
 * @tnote Last Branch Update - master
 *
 * @tnote Created on 2019-01-18 by Saksham
 * @tnote Updates :
 */
class ChildCategoryAdapter(private val lifecycle: Lifecycle,
                           private val ids: List<String>,
                           private val select: Boolean) : RecyclerView.Adapter<ChildCategoryViewHolder>() {
    private lateinit var model: CategoryModel

    // no of items
    override fun getItemCount(): Int {
        return ids.size
    }

    // on create view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildCategoryViewHolder {
        model = CategoryModel(lifecycle, parent.context)
        return ChildCategoryViewHolder(parent, lifecycle, select)
    }

    // bind view
    override fun onBindViewHolder(holder: ChildCategoryViewHolder, position: Int) {
        model.getItem(ids[position]) {
            holder.bindTo(it)
        }
    }

}