package org.sourcei.xpns.adapter

import android.app.Activity
import android.content.Context
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
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
class TransactionsAdapter(
    private val lifecycle: Lifecycle,
    private val items: List<TransactionCPojo>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val NAME = "TransactionAdapter"
    private lateinit var context: Context
    private lateinit var activity: Activity
    private val MAIN = 0
    private val TRANSACTION = 1

    // get no of items
    override fun getItemCount(): Int {
        return items.size + 1
    }

    // type of item
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
                    holder.bindTo(items[position - 1], items[position - 2])
                else
                    holder.bindTo(items[position - 1])
            }

            if (holder is MainViewHolder)
                holder.bindTo()
        }
    }

}