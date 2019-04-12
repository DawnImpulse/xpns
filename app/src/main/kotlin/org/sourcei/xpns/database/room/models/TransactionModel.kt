package org.sourcei.xpns.database.room.models

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.sourcei.xpns.database.room.dao.TransactionDao
import org.sourcei.xpns.ui.objects.ViewTransactionObject
import org.sourcei.xpns.database.room.objects.TransactionPojo
import org.sourcei.xpns.database.room.RoomSource
import org.sourcei.xpns.utils.C
import org.sourcei.xpns.utils.others.Config
import org.sourcei.xpns.utils.F
import org.sourcei.xpns.utils.events.Post
import org.sourcei.xpns.utils.extensions.runOnUiThread
import java.util.*

/**
 *
 * @author - Saksham
 * @note Last Branch Update - master
 *
 * @note Created on 2018-08-13 by Saksham
 * @note Updates :
 * Saksham - 2018 09 05 - master - transaction factory model
 * Saksham - 2018 09 12 - master - get balance, expense & saving
 */
class TransactionModel(private val lifecycle: Lifecycle, private val context: Context) {

    // dao instance
    private fun dao(): TransactionDao {
        return RoomSource
            .getInstance(context, Config.DbName)
            .transactionsDao()
    }

    // insert a new transaction
    fun insert(pojo: TransactionPojo) {
        GlobalScope.launch {

            val value = pojo.apply {
                tid = UUID.randomUUID().toString()
            }

            dao().insert(value)

            context.runOnUiThread {
                Post.newTransaction(value)
            }

            // date convert -> DateHandler.fullToDate(date, time)
        }
    }

    // fetching a single item
    fun getItem(id: String, wallet: String) {
        GlobalScope.launch {
            val item = dao().getItem(id, wallet)
            context.runOnUiThread {

            }
        }
    }

    // get all items
    fun getItems(wallet: String, callback: (List<ViewTransactionObject>) -> Unit) {
        GlobalScope.launch {
            val items = dao().getItems(wallet)
            lifecycle.addObserver(object : LifecycleObserver {
                var once = true
                @OnLifecycleEvent(Lifecycle.Event.ON_START)
                fun onStart() {
                    if (once) {
                        once = false
                        activity.runOnUiThread {
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