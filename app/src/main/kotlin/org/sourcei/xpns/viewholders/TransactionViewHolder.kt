package org.sourcei.xpns.viewholders

import android.app.Activity
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import com.google.gson.Gson
import kotlinx.android.synthetic.main.inflator_transactions.view.*
import org.sourcei.xpns.R
import org.sourcei.xpns.activities.ViewTransactionActivity
import org.sourcei.xpns.handlers.DateHandler
import org.sourcei.xpns.handlers.ImageHandler
import org.sourcei.xpns.pojo.TransactionCPojo
import org.sourcei.xpns.utils.C
import org.sourcei.xpns.utils.Colors
import org.sourcei.xpns.utils.gone
import org.sourcei.xpns.utils.openActivity

/**
 * @info -
 *
 * @author - Saksham
 * @tnote Last Branch Update - master
 *
 * @tnote Created on 2018-09-05 by Saksham
 * @tnote Updates :
 */
class TransactionViewHolder(
    private val parent: ViewGroup,
    private val lifecycle: Lifecycle
) : androidx.recyclerview.widget.RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.inflator_transactions, parent, false)
) {

    private val image = itemView.transactionImage
    private val name = itemView.transactionName
    private val amount = itemView.transactionAmount
    private val note = itemView.transactionNote
    private val date = itemView.transactionDate
    private val layout = itemView.transactionLayout

    private val NAME = "TransactionViewHolder"
    private val context = parent.context

    // binding data to layout
    fun bindTo(transaction: TransactionCPojo?, lastItem: TransactionCPojo? = null) {
        transaction?.let {
            // if last item is present
            lastItem?.let { last ->

                val current = DateHandler.convertStoredToVisible(transaction.obj.tdate)
                val prev = DateHandler.convertStoredToVisible(last.obj.tdate)

                // checking if last item (formatted) match current view date
                if (current == prev)
                    date.gone()
            }

            // setting icon in view
            ImageHandler.setImageInView(lifecycle, image, transaction.cat.cicon.iurls!!.url64)

            // setting amount, cat name & date
            amount.text = transaction.obj.tamount.toString()
            name.text = transaction.cat.cname
            date.text = DateHandler.convertStoredToVisible(transaction.obj.tdate)
            name.setTextColor(Color.parseColor(transaction.cat.ccolor))

            // if note is not empty
            transaction.obj.tnote?.let {
                note.text = it
            }

            // checking type of transaction for color
            if (transaction.cat.ctype == C.EXPENSE)
                amount.setTextColor(Colors(context).EXPENSE)
            else
                amount.setTextColor(Colors(context).SAVING)

            // on click of view
            layout.setOnClickListener { _ ->
                (context as Activity).openActivity(ViewTransactionActivity::class.java) {
                    putString(C.TRANSACTION, Gson().toJson(it))
                }
            }
        }
    }
}