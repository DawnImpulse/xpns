package org.sourcei.xpns.models

import android.app.Activity
import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.sourcei.xpns.dao.TransactionDao
import org.sourcei.xpns.handlers.DateHandler
import org.sourcei.xpns.pojo.TransactionCPojo
import org.sourcei.xpns.pojo.TransactionPojo
import org.sourcei.xpns.source.RoomSource
import org.sourcei.xpns.utils.C
import org.sourcei.xpns.utils.Config
import org.sourcei.xpns.utils.F
import java.util.*

/**
 *
 * @author - Saksham
 * @tnote Last Branch Update - master
 *
 * @tnote Created on 2018-08-13 by Saksham
 * @tnote Updates :
 * Saksham - 2018 09 05 - master - transaction factory model
 * Saksham - 2018 09 12 - master - get balance, expense & saving
 */
class TransactionModel(private val lifecycle: Lifecycle, private val context: Context) : ViewModel() {
    private lateinit var activity: Activity

    init {
        activity = context as Activity
    }

    // dao instance
    private fun dao(): TransactionDao {
        return RoomSource
            .getInstance(context, Config.DbName)
            .transactionsDao()
    }

    // insert a new transaction
    fun insert(amount: String, cid: String, date: String, time: String, note: String?, wallet: String) {
        GlobalScope.launch {
            dao().insert(
                TransactionPojo(
                    0,
                    UUID.randomUUID().toString(),
                    amount.toDouble(),
                    cid,
                    false,
                    DateHandler.fullToDate(date, time),
                    note,
                    wallet
                )
            )
        }
    }

    // fetching a single item
    fun getItem(id: String, wallet: String, callback: (TransactionCPojo?) -> Unit) {
        GlobalScope.launch {
            val item = dao().getItem(id, wallet)
            lifecycle.addObserver(object : LifecycleObserver {
                var once = true
                @OnLifecycleEvent(Lifecycle.Event.ON_START)
                fun onStart() {
                    if (once) {
                        once = false
                        activity.runOnUiThread {
                            callback(item)
                        }
                    }
                }
            })
        }
    }

    // get all items
    fun getItems(wallet: String, callback: (List<TransactionCPojo>) -> Unit) {
        GlobalScope.launch {
            val items = dao().getItems(wallet)
            lifecycle.addObserver(object : LifecycleObserver {
                var once = true
                @OnLifecycleEvent(Lifecycle.Event.ON_START)
                fun onStart() {
                    if (once) {
                        once = false
                        activity.runOnUiThread{
                            callback(items)
                        }
                    }
                }
            })
        }
    }

    //update an item
    fun editItem(transactionPojo: TransactionPojo) {
        GlobalScope.launch { dao().insert(transactionPojo) }
    }

    //delete an item
    fun deleteItem(transactionPojo: TransactionPojo) {
        GlobalScope.launch { dao().deleteItem(transactionPojo) }
    }

    //delete an item
    fun deleteItem(id: String) {
        GlobalScope.launch { dao().deleteItem(id) }
    }

    // get balance
    fun getBalance(wallet: String, callback: (Double) -> Unit) {
        GlobalScope.launch {
            val saving = dao().getTotal(C.SAVING, wallet)
            val expense = dao().getTotal(C.EXPENSE, wallet)

            lifecycle.addObserver(object : LifecycleObserver {
                var once = true
                @OnLifecycleEvent(Lifecycle.Event.ON_START)
                fun onStart() {
                    if (once) {
                        once = false
                        activity.runOnUiThread {
                            callback(F.roundOff2Decimal(saving.total - expense.total))
                        }
                    }
                }
            })
        }
    }
}