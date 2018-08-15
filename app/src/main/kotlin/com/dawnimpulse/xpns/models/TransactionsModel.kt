package com.dawnimpulse.xpns.models

import android.content.Context
import com.dawnimpulse.xpns.dao.TransactionsDao
import com.dawnimpulse.xpns.pojo.TransactionPojo
import com.dawnimpulse.xpns.utils.Config
import com.dawnimpulse.xpns.utils.F
import com.dawnimpulse.xpns.utils.XpnsRoom
import kotlinx.coroutines.experimental.launch
import java.util.*

/**
 * @info - creating direct functions to be used by other classes
 *
 * @author - Saksham
 * @note Last Branch Update - master
 *
 * @note Created on 2018-08-13 by Saksham
 * @note Updates :
 */
class TransactionsModel(private val context: Context) {

    // dao instance
    private fun dao(): TransactionsDao {
        return XpnsRoom
                .getInstance(context, Config.DbName)
                .transactionsDao()
    }

    // insert a new transaction
    fun insert(amount: String, cid: String, date: String, time: String, note: String) {
        launch {
            dao().insert(
                    TransactionPojo(
                            UUID.randomUUID().toString(),
                            amount.toDouble(),
                            cid,
                            false,
                            F.toDate(date, time),
                            note
                    ))
        }
    }

    // fetching a single item
    fun getItem(_id: String, callback: (TransactionPojo?) -> Unit) {
        launch {
            var items = dao().getItem(_id)
            var item = if (items != null) items[0] else null
            callback(item)
        }
    }

    // get items after given _id in sorted manner
    fun getItems(page: Int, limit: Int, callback: (List<TransactionPojo>?) -> Unit) {
        launch {
            var items = dao().getItems(page, limit)
            callback(items)
        }
    }

    //get all transactions
    fun getAll(callback: (List<TransactionPojo>?) -> Unit) {
        launch {
            var items = dao().getAll()
            callback(items)
        }
    }

    //update an item
    fun editItem(transactionPojo: TransactionPojo) {
        launch { dao().insert(transactionPojo) }
    }

    //delete an item
    fun deleteItem(transactionPojo: TransactionPojo) {
        launch { dao().deleteItem(transactionPojo) }
    }
}