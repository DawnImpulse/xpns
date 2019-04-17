package org.sourcei.xpns.database.room.models

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.sourcei.xpns.database.room.RoomSource
import org.sourcei.xpns.database.room.dao.TransactionDao
import org.sourcei.xpns.database.room.objects.TransactionPojo
import org.sourcei.xpns.ui.activities.ModularActivity
import org.sourcei.xpns.utils.C
import org.sourcei.xpns.utils.F
import org.sourcei.xpns.utils.events.Post
import org.sourcei.xpns.utils.others.Config
import org.sourcei.xpns.utils.others.Lifecycle

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
class TransactionModel(private val activity: ModularActivity) {

    // dao instance
    private fun dao(): TransactionDao {
        return RoomSource
            .getInstance(activity.context, Config.DbName)
            .transactionsDao()
    }

    // insert a new transaction
    fun insert(pojo: TransactionPojo) {

        GlobalScope.launch {

            // insert item
            dao().insert(pojo)

            // send event on UI thread (on start)
            Lifecycle.onStart(activity) {
                Post.newTransaction(pojo)
            }

            // date convert -> DateHandler.fullToDate(date, time)
        }
    }

    // fetching a single item
    fun getItem(id: String, wallet: String) {
        GlobalScope.launch {

            // fetch item
            val item = dao().getItem(id, wallet)

            // send event on UI thread (on start0
            Lifecycle.onStart(activity) {
                Post.singleTransaction(item)
            }
        }
    }

    // get all items
    fun getItems(wallet: String) {
        GlobalScope.launch {

            // fetch items
            val items = dao().getItems(wallet)

            // send event on UI thread (on start0
            Lifecycle.onStart(activity) {
                Post.multipleTransactions(items)
            }
        }
    }

    //update an item
    fun editItem(pojo: TransactionPojo) {
        GlobalScope.launch {

            // edit item
            dao().insert(pojo)

            // send on UI thread (on start)
            Lifecycle.onStart(activity) {
                Post.editTransaction(pojo)
            }
        }
    }

    //delete an item
    fun deleteItem(tid: String) {
        GlobalScope.launch {

            // delete by tid
            dao().deleteItem(tid)

            // send event on UI (on start)
            Lifecycle.onStart(activity) {
                Post.deleteTransaction(tid)
            }
        }
    }

    // get balance
    fun getBalance(wallet: String) {
        GlobalScope.launch {

            // getting total saving & expense amount
            val saving = dao().getTotal(C.SAVING, wallet)
            val expense = dao().getTotal(C.EXPENSE, wallet)

            // update balance on UI thread (on start)
            Lifecycle.onStart(activity) {
                Config.balance.value = F.roundOff2Decimal(saving.total - expense.total)
            }
        }
    }
}