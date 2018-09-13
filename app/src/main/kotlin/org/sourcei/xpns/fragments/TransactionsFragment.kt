package org.sourcei.xpns.fragments


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_transactions.*
import org.sourcei.xpns.R
import org.sourcei.xpns.adapter.TransactionsAdapter
import org.sourcei.xpns.handlers.DateHandler
import org.sourcei.xpns.models.TransactionModel
import org.sourcei.xpns.models.TransactionModelFactory
import org.sourcei.xpns.utils.Colors
import org.sourcei.xpns.utils.F
import java.util.*


/**
 * @info -
 *
 * @author - Saksham
 * @tnote Last Branch Update - master
 *
 * @tnote Created on 2018-08-19 by Saksham
 * @tnote Updates :
 * Saksham - 2018 09 12 - master - dynamic counters
 */
class TransactionsFragment : Fragment() {
    private lateinit var model: TransactionModel
    private lateinit var adapter: TransactionsAdapter
    private lateinit var calendar: Calendar

    // on create view
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_transactions, container, false)
    }

    // on view created
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model = ViewModelProviders.of(this, TransactionModelFactory(lifecycle, context!!)).get(TransactionModel::class.java)
        adapter = TransactionsAdapter(lifecycle)
        calendar = Calendar.getInstance()

        month.text = F.capWord(DateHandler.convertMMtoMMMM("${calendar.get(Calendar.MONTH) + 1}"))
        year.text = calendar.get(Calendar.YEAR).toString()

        model.getItems().observe(this, Observer {
            adapter.submitList(it)
        })
        transactionsRecycler.layoutManager = LinearLayoutManager(context)
        transactionsRecycler.adapter = adapter
    }

    // on resume , getting balance
    override fun onResume() {
        super.onResume()
        model.getBalance {
            activity!!.runOnUiThread {
                balance.text = "$ $it"
                if (it < 0)
                    balance.setTextColor(Colors(context!!).EXPENSE)
                else
                    balance.setTextColor(Colors(context!!).SAVING)
            }
        }
    }
}
