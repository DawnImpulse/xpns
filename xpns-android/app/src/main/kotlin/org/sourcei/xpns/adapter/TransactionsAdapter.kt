package org.sourcei.xpns.adapter

import android.app.Activity
import android.content.Context
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
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
    private val NAME = "TransactionAdapter"
    private lateinit var context: Context
    private lateinit var activity: Activity

    // on create view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        context = parent.context
        activity = context as Activity
        return TransactionViewHolder(parent, lifecycle)
    }

    // bind view
    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        activity.runOnUiThread {
            holder.bindTo(getItem(position))
        }
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