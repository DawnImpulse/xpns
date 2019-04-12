package org.sourcei.xpns.ui.viewholders

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.inflator_overview_1.view.*
import org.sourcei.xpns.utils.extensions.toCapital
import org.sourcei.xpns.utils.handlers.DateHandler
import org.sourcei.xpns.utils.others.Colors
import org.sourcei.xpns.utils.others.Config

/**
 * @info -
 *
 * @author - Saksham
 * @tnote Last Branch Update - master
 *
 * @tnote Created on 2019-01-25 by Saksham
 * @tnote Updates :
 */
@SuppressLint("SetTextI18n")
class OverviewViewHolder1(parent: ViewGroup) : RecyclerView.ViewHolder(Inflator.Overview1(parent)) {

    // context
    private val context = parent.context

    // views
    private val month = itemView.mainMonth
    private val year = itemView.mainYear
    private val balance = itemView.mainBalance

    // ----------------------------------------------------

    // binding data to layout
    fun bindData() {
        val smallMonth = DateHandler.getCurrentMonthInt().toString() //current month in int
        val fullMonth = DateHandler.convertMMtoMMMM(smallMonth) //current month full string
        val yearInt = DateHandler.getCurrentYear() //current year

        month.text = fullMonth.toCapital() // set month
        year.text = yearInt.toString() // set year

        balance(Config.balance.value) // set balance & color

        // listener for balance change
        Config.balance.onChange {
            balance(Config.balance.value)
        }

    }

    // change balance & color
    private fun balance(bal: Double) {
        balance.text = "${Config.currency} $bal"
        if (bal < 0)
            balance.setTextColor(Colors(context).EXPENSE)
        else
            balance.setTextColor(Colors(context).SAVING)
    }
}