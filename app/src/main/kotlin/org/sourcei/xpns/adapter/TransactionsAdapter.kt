package org.sourcei.xpns.adapter

import android.arch.lifecycle.Lifecycle
import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.view.ViewGroup
import org.sourcei.xpns.pojo.TransactionCPojo
import org.sourcei.xpns.viewholders.TransactionViewHolder

/**
 * @info -
 *
 * @author - Saksham
 * @tnote Last Branch Update - master
 *
 * @tnote Created on 2018-09-05 by Saksham
 * @tnote Updates :
 */
class TransactionsAdapter(private val lifecycle: Lifecycle)
    : PagedListAdapter<TransactionCPojo, TransactionViewHolder>(diffCallback) {

    // on create view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : TransactionViewHolder = TransactionViewHolder(parent, lifecycle)

    // bind view
    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    // used for diff util
    companion object {

        private val diffCallback = object : DiffUtil.ItemCallback<TransactionCPojo>() {

            override fun areItemsTheSame(oldItem: TransactionCPojo, newItem: TransactionCPojo): Boolean =
                    oldItem.obj.tid == newItem.obj.tid

            /**
             * Note that in kotlin, == checking on data classes compares all contents, but in Java,
             * typically you'll implement Object#equals, and use it to compare object contents.
             */
            override fun areContentsTheSame(oldItem: TransactionCPojo, newItem: TransactionCPojo): Boolean =
                    oldItem == newItem
        }
    }

}