package org.sourcei.xpns.viewholders

import android.arch.lifecycle.Lifecycle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import kotlinx.android.synthetic.main.inflator_transactions.view.*
import org.sourcei.xpns.R
import org.sourcei.xpns.handlers.ImageHandler
import org.sourcei.xpns.pojo.TransactionCPojo

/**
 * @info -
 *
 * @author - Saksham
 * @note Last Branch Update - master
 *
 * @note Created on 2018-09-05 by Saksham
 * @note Updates :
 */
class TransactionViewHolder(private val parent: ViewGroup,
                            private val lifecycle: Lifecycle) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.inflator_transactions, parent, false)) {

    private val image = itemView.transactionImage
    private val name = itemView.transactionName
    private val amount = itemView.transactionAmount
    private val note = itemView.transactionNote

    // binding data to layout
    fun bindTo(transaction: TransactionCPojo?) {
        transaction?.let {
            ImageHandler.setImageInView(lifecycle, image, transaction.icon.urls!!.url64)
            amount.text = transaction.amount.toString()
            name.text = transaction.name
            transaction.note?.let {
                note.text = it
            }
        }
    }
}