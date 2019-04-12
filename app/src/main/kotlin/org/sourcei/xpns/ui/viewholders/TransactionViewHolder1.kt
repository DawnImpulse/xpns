package org.sourcei.xpns.ui.viewholders

import android.app.Activity
import android.graphics.Color
import android.view.ViewGroup
import com.google.gson.Gson
import kotlinx.android.synthetic.main.inflator_transactions.view.*
import org.sourcei.xpns.ui.activities.ViewTransactionActivity
import org.sourcei.xpns.ui.objects.ViewTransactionObject
import org.sourcei.xpns.utils.C
import org.sourcei.xpns.utils.extensions.gone
import org.sourcei.xpns.utils.extensions.openActivity
import org.sourcei.xpns.utils.handlers.DateHandler
import org.sourcei.xpns.utils.handlers.ImageHandler
import org.sourcei.xpns.utils.others.Colors
import org.sourcei.xpns.utils.others.Observe

/**
 * @info -
 *
 * @author - Saksham
 * @tnote Last Branch Update - master
 *
 * @tnote Created on 2018-09-05 by Saksham
 * @tnote Updates :
 */
class TransactionViewHolder1(
    // parent view group
    private val parent: ViewGroup

) : ModularViewHolder(Inflator.Transaction1(parent)) {

    // views
    private val image = itemView.transactionImage
    private val name = itemView.transactionName
    private val amount = itemView.transactionAmount
    private val note = itemView.transactionNote
    private val date = itemView.transactionDate
    private val layout = itemView.transactionLayout

    // ------------------------------------------------------------

    // binding data to layout
    fun bindData(currentItem: Observe<ViewTransactionObject>, lastItem: Observe<ViewTransactionObject>? = null) {

        // if last item is present
        // check to see if we need to show transaction under same date
        lastItem?.let {
            val last = it.value

            // current date
            val currentDate = DateHandler.convertStoredToVisible(currentItem.value.obj.tdate)

            // prev date
            val prevDate = DateHandler.convertStoredToVisible(last.obj.tdate)

            // checking if last item (formatted) match current view date
            if (currentDate == prevDate)
                date.gone()
        }

        // set data & observer
        setData(currentItem.value)
        currentItem.onChange {
            setData(currentItem.value)
        }

        // on click of view
        layout.setOnClickListener {
            (context as Activity).openActivity(ViewTransactionActivity::class.java) {
                putString(C.TRANSACTION, Gson().toJson(it))
            }
        }
    }

    // set transaction data in view
    private fun setData(item: ViewTransactionObject) {

        // setting icon in view
        ImageHandler.inView(lifecycle, image, item.cat.cicon.iurls!!.url64)

        // setting amount, cat name & date
        amount.text = item.obj.tamount.toString()
        name.text = item.cat.cname
        date.text = DateHandler.convertStoredToVisible(item.obj.tdate)
        name.setTextColor(Color.parseColor(item.cat.ccolor))

        // if note is not empty
        item.obj.tnote?.let {
            note.text = it
        }

        // checking type of viewTransaction for color
        if (item.cat.ctype == C.EXPENSE)
            amount.setTextColor(Colors(context).EXPENSE)
        else
            amount.setTextColor(Colors(context).SAVING)
    }
}