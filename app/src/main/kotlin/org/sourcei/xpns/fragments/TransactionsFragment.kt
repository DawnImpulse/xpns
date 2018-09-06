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
import org.sourcei.xpns.models.TransactionModel
import org.sourcei.xpns.models.TransactionModelFactory


/**
 * @info -
 *
 * @author - Saksham
 * @tnote Last Branch Update - master
 *
 * @tnote Created on 2018-08-19 by Saksham
 * @tnote Updates :
 */
class TransactionsFragment : Fragment() {
    private lateinit var model:TransactionModel
    private lateinit var adapter:TransactionsAdapter

    // on create view
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_transactions, container, false)
    }

    // on view created
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model = ViewModelProviders.of(this,TransactionModelFactory(lifecycle,context!!)).get(TransactionModel::class.java)
        adapter = TransactionsAdapter(lifecycle)
        model.getItems().observe(this, Observer {
            adapter.submitList(it)
        })
        transactionsRecycler.layoutManager = LinearLayoutManager(context)
        transactionsRecycler.adapter = adapter
    }
}
