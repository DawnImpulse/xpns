package org.sourcei.xpns.adapter

import android.arch.lifecycle.Lifecycle
import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.view.ViewGroup
import org.sourcei.xpns.pojo.CategoryPojo
import org.sourcei.xpns.viewholders.CategoryViewHolder

/**
 * @info -
 *
 * @author - Saksham
 * @note Last Branch Update - master
 *
 * @note Created on 2018-09-04 by Saksham
 * @note Updates :
 */
class CategoryAdapter(private val lifecycle: Lifecycle,
                      private val select: Boolean) : PagedListAdapter<CategoryPojo, CategoryViewHolder>(diffCallback) {

    // on create view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : CategoryViewHolder = CategoryViewHolder(parent,lifecycle,select)

    // bind view
    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    // used for diff util
    companion object {

        private val diffCallback = object : DiffUtil.ItemCallback<CategoryPojo>() {

            override fun areItemsTheSame(oldItem: CategoryPojo, newItem: CategoryPojo): Boolean =
                    oldItem.id == newItem.id

            /**
             * Note that in kotlin, == checking on data classes compares all contents, but in Java,
             * typically you'll implement Object#equals, and use it to compare object contents.
             */
            override fun areContentsTheSame(oldItem: CategoryPojo, newItem: CategoryPojo): Boolean =
                    oldItem == newItem
        }
    }

}