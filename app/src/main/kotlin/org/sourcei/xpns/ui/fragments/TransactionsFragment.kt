package org.sourcei.xpns.ui.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_transactions.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.sourcei.xpns.ui.adapter.TransactionsAdapter
import org.sourcei.xpns.database.room.models.TransactionModel
import org.sourcei.xpns.utils.C
import org.sourcei.xpns.utils.others.Config
import org.sourcei.xpns.utils.extensions.runOnUiThread


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
    private var adapter: TransactionsAdapter? = null

    // on create view
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(org.sourcei.xpns.R.layout.fragment_transactions, container, false)
    }

    // on view created
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model = TransactionModel(lifecycle, context!!)
        model.getItems(Config.WALLET) {
            adapter = TransactionsAdapter(lifecycle, it)
            transactionsRecycler.layoutManager = LinearLayoutManager(context!!)
            transactionsRecycler.adapter = adapter
        }
    }

    // on start
    override fun onStart() {
        super.onStart()
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this)
    }

    // on resume , getting balance
    override fun onResume() {
        super.onResume()
        adapter?.notifyItemChanged(0)
    }

    // on destroy
    override fun onDestroy() {
        super.onDestroy()
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: Event) {
        if (event.obj.has(C.TYPE)) {
            when (event.obj.getString(C.TYPE)) {
                C.NEW_TRANSACTION -> {
                    GlobalScope.launch {
                        delay(1000)
                        runOnUiThread {
                            model.getItems(Config.WALLET) {
                                adapter = TransactionsAdapter(lifecycle, it)
                                transactionsRecycler.layoutManager = LinearLayoutManager(context!!)
                                transactionsRecycler.adapter = adapter
                            }
                        }
                    }
                }
            }
        }
    }
}
