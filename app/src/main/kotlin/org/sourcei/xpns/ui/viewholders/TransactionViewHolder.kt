package org.sourcei.xpns.ui.viewholders

import android.app.Activity
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import com.google.gson.Gson
import kotlinx.android.synthetic.main.inflator_transactions.view.*
import org.sourcei.xpns.R
import org.sourcei.xpns.ui.activities.ViewTransactionActivity
import org.sourcei.xpns.utils.handlers.DateHandler
import org.sourcei.xpns.utils.handlers.ImageHandler
import org.sourcei.xpns.ui.objects.ViewTransactionObject
import org.sourcei.xpns.utils.C
import org.sourcei.xpns.utils.others.Colors
import org.sourcei.xpns.utils.extensions.gone
import org.sourcei.xpns.utils.extensions.openActivity

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
    fun bindTo(viewTransaction: ViewTransactionObject?, lastItem: ViewTransactionObject? = null) {
        viewTransaction?.let {
            // if last item is present
            lastItem?.let { last ->

                val current = DateHandler.convertStoredToVisible(viewTransaction.obj.tdate)
                val prev = DateHandler.convertStoredToVisible(last.obj.tdate)

                // checking if last item (formatted) match current view date
                if (current == prev)
                    date.gone()
            }

            // setting icon in view
            ImageHandler.setImageInView(lifecycle, image, viewTransaction.cat.cicon.iurls!!.url64)

            // setting amount, cat name & date
            amount.text = viewTransaction.obj.tamount.toString()
            name.text = viewTransaction.cat.cname
            date.text = DateHandler.convertStoredToVisible(viewTransaction.obj.tdate)
            name.setTextColor(Color.parseColor(viewTransaction.cat.ccolor))

            // if note is not empty
            viewTransaction.obj.tnote?.let {
                note.text = it
            }

            // checking type of viewTransaction for color
            if (viewTransaction.cat.ctype == C.EXPENSE)
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