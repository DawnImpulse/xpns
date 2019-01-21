package org.sourcei.xpns.viewholders

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import kotlinx.android.synthetic.main.inflator_transactions.view.*
import org.sourcei.xpns.R
import org.sourcei.xpns.handlers.ImageHandler
import org.sourcei.xpns.pojo.TransactionCPojo
import org.sourcei.xpns.utils.C
import org.sourcei.xpns.utils.Colors

/**
 * @info -
 *
 * @author - Saksham
 * @tnote Last Branch Update - master
 *
 * @tnote Created on 2018-09-05 by Saksham
 * @tnote Updates :
 */
class TransactionViewHolder(private val parent: ViewGroup,
                            private val lifecycle: Lifecycle) : androidx.recyclerview.widget.RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.inflator_transactions, parent, false)) {

    private val image = itemView.transactionImage
    private val name = itemView.transactionName
    private val amount = itemView.transactionAmount
    private val note = itemView.transactionNote
    private val context = parent.context

    // binding data to layout
    fun bindTo(transaction: TransactionCPojo?) {
        transaction?.let {
            ImageHandler.setImageInView(lifecycle, image, transaction.cat.cicon.iurls!!.url64)
            amount.text = transaction.obj.tamount.toString()
            name.text = transaction.cat.cname
            name.setTextColor(Color.parseColor(transaction.cat.ccolor))
            transaction.obj.tnote?.let {
                note.text = it
            }
            if(transaction.cat.ctype == C.EXPENSE)
                amount.setTextColor(Colors(context).EXPENSE)
            else
                amount.setTextColor(Colors(context).SAVING)
        }
    }
}