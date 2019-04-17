package org.sourcei.xpns.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.sourcei.xpns.ui.objects.ViewTransactionObject
import org.sourcei.xpns.ui.viewholders.OverviewViewHolder1
import org.sourcei.xpns.ui.viewholders.TransactionViewHolder1
import org.sourcei.xpns.utils.others.Observe
import org.sourcei.xpns.utils.reusables.T

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

    private val transactions: List<Observe<ViewTransactionObject>>, // list of transactions
    private val viewTypeTransaction: Int, //type of view for transaction
    private val viewTypeOverview: Int //type of view for overview (-1 for no view)

) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    // get no of items
    override fun getItemCount(): Int {
        // return size + 1 if overview needs to be shown
        return transactions.size + if (viewTypeOverview == T.OVERVIEW_0) 0 else 1
    }

    // type of item
    override fun getItemViewType(position: Int): Int {
        // if overview if present
        return if (viewTypeOverview == T.OVERVIEW_0) {
            return if (position == 0) viewTypeOverview else viewTypeTransaction
        } else
            viewTypeTransaction
    }

    // on create view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            T.OVERVIEW_1 -> OverviewViewHolder1(parent)
            else -> TransactionViewHolder1(parent)
        }
    }

    // bind view
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is OverviewViewHolder1)
            holder.bindData()

        if (holder is TransactionViewHolder1) {
            if (position > 0)
                holder.bindData(transactions[position], transactions[position - 1])
            else
                holder.bindData(transactions[position], null)
        }
    }

}