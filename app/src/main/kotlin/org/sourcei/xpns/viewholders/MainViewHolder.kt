package org.sourcei.xpns.viewholders

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.inflator_main.view.*
import org.sourcei.xpns.R
import org.sourcei.xpns.handlers.DateHandler
import org.sourcei.xpns.models.TransactionModel
import org.sourcei.xpns.utils.Colors
import org.sourcei.xpns.utils.Config
import org.sourcei.xpns.utils.F
import java.util.*

/**
 * @info -
 *
 * @author - Saksham
 * @tnote Last Branch Update - master
 *
 * @tnote Created on 2019-01-25 by Saksham
 * @tnote Updates :
 */
class MainViewHolder(
    private val parent: ViewGroup,
    private val lifecycle: Lifecycle
) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.inflator_main, parent, false)) {

    private val NAME = "MainViewHolder"
    private val context = parent.context

    private val month = itemView.mainMonth
    private val year = itemView.mainYear
    private val balance = itemView.mainBalance

    // binding data to layout
    fun bindTo() {
        val calendar = Calendar.getInstance()
        val model = TransactionModel(lifecycle, context)


        month.text = F.capWord(DateHandler.convertMMtoMMMM("${calendar.get(Calendar.MONTH) + 1}"))
        year.text = calendar.get(Calendar.YEAR).toString()

        model.getBalance(Config.WALLET) {
            (parent.context as Activity).runOnUiThread {
                balance.text = "$ $it"
                if (it < 0)
                    balance.setTextColor(Colors(context).EXPENSE)
                else
                    balance.setTextColor(Colors(context).SAVING)
            }
        }
    }
}