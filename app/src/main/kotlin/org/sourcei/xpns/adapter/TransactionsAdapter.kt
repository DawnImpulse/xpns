package org.sourcei.xpns.adapter

import android.app.Activity
import android.content.Context
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import org.sourcei.xpns.pojo.TransactionCPojo
import org.sourcei.xpns.viewholders.MainViewHolder
import org.sourcei.xpns.viewholders.TransactionViewHolder

/**
 * @info -
 *
 * @author - Saksham
 * @tnote Last Branch Update - master
 *
 * @tnote Created on 2018-09-05 by Saksham
 * @tnote Updates :
 *  Saksham - 2019 01 25 - master - main view holder
 */
class TransactionsAdapter(private val lifecycle: Lifecycle) :
    PagedListAdapter<TransactionCPojo, RecyclerView.ViewHolder>(diffCallback) {
    private val NAME = "TransactionAdapter"
    private lateinit var context: Context
    private lateinit var activity: Activity
    private val MAIN = 0
    private val TRANSACTION = 1

    override fun getItemCount(): Int {
        return super.getItemCount() + 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) MAIN else TRANSACTION
    }

    // on create view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        activity = context as Activity

        return if (viewType == MAIN)
            MainViewHolder(parent, lifecycle)
        else
            TransactionViewHolder(parent, lifecycle)
    }

    // bind view
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        activity.runOnUiThread {
            if (holder is TransactionViewHolder) {
                if (position > 1)
                    holder.bindTo(getItem(position - 1), getItem(position - 2))
                else
                    holder.bindTo(getItem(position - 1))
            }

            if (holder is MainViewHolder)
                holder.bindTo()
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