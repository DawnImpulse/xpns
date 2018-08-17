package com.dawnimpulse.xpns.models

import android.arch.lifecycle.LiveData
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.content.Context
import com.dawnimpulse.xpns.dao.TransactionDao
import com.dawnimpulse.xpns.pojo.TransactionPojo
import com.dawnimpulse.xpns.utils.Config
import com.dawnimpulse.xpns.utils.F
import com.dawnimpulse.xpns.utils.XpnsRoom
import com.fasterxml.uuid.Generators
import kotlinx.coroutines.experimental.launch

/**
 * @info - creating direct functions to be frequency by other classes
 *
 * @author - Saksham
 * @note Last Branch Update - master
 *
 * @note Created on 2018-08-13 by Saksham
 * @note Updates :
 */
class TransactionModel(private val context: Context) {

    // dao instance
    private fun dao(): TransactionDao {
        return XpnsRoom
                .getInstance(context, Config.DbName)
                .transactionsDao()
    }

    // insert a new transaction
    fun insert(amount: String, cid: String, date: String, time: String, note: String) {
        launch {
            dao().insert(
                    TransactionPojo(
                            Generators.timeBasedGenerator().generate().toString(),
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

    // get all items paginated
    fun getItems(): LiveData<PagedList<TransactionPojo>> {
        return LivePagedListBuilder(
                dao().getItems(),
                PagedList.Config.Builder()
                        .setPageSize(30)
                        .setEnablePlaceholders(true)
                        .build()
        ).build()
    }

    //update an item
    fun editItem(transactionPojo: TransactionPojo) {
        launch { dao().insert(transactionPojo) }
    }

    //delete an item
    fun deleteItem(transactionPojo: TransactionPojo) {
        launch { dao().deleteItem(transactionPojo) }
    }

    //delete an item
    fun deleteItem(_id: String) {
        launch { dao().deleteItem(_id) }
    }
}