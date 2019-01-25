package org.sourcei.xpns.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_transactions.*
import org.sourcei.xpns.R
import org.sourcei.xpns.adapter.TransactionsAdapter
import org.sourcei.xpns.models.TransactionModel
import org.sourcei.xpns.models.TransactionModelFactory
import org.sourcei.xpns.utils.Config


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

        model.getItems(Config.WALLET).observe(this, Observer {
            adapter.submitList(it)
        })
        transactionsRecycler.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        transactionsRecycler.adapter = adapter
    }

    // on resume , getting balance
    override fun onResume() {
        super.onResume()
        adapter.notifyItemChanged(0)
    }
}
