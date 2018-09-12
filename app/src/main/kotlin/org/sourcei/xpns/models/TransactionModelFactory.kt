package org.sourcei.xpns.models

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.content.Context
import kotlinx.coroutines.experimental.launch
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
 * @info - creating direct functions to be cfrequency by other classes
 *
 * @author - Saksham
 * @tnote Last Branch Update - master
 *
 * @tnote Created on 2018-08-13 by Saksham
 * @tnote Updates :
 * Saksham - 2018 09 05 - master - transaction factory model
 * Saksham - 2018 09 12 - master - get balance, expense & saving
 */
class TransactionModelFactory(private val lifecycle: Lifecycle, private val context: Context)
    : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TransactionModel(lifecycle, context) as T
    }

}

class TransactionModel(private val lifecycle: Lifecycle, private val context: Context) : ViewModel() {

    // dao instance
    private fun dao(): TransactionDao {
        return RoomSource
                .getInstance(context, Config.DbName)
                .transactionsDao()
    }

    // insert a new transaction
    fun insert(amount: String, cid: String, date: String, time: String, note: String?) {
        launch {
            dao().insert(
                    TransactionPojo(
                            0,
                            UUID.randomUUID().toString(),
                            amount.toDouble(),
                            cid,
                            false,
                            DateHandler.fullToDate(date, time),
                            note
                    ))
        }
    }

    // fetching a single item
    fun getItem(id: String, callback: (TransactionCPojo?) -> Unit) {
        launch {
            callback(dao().getItem(id))
        }
    }

    // get all items paginated
    fun getItems(): LiveData<PagedList<TransactionCPojo>> {
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
    fun deleteItem(id: String) {
        launch { dao().deleteItem(id) }
    }

    // get balance
    fun getBalance(callback: (Double) -> Unit) {
        launch {
            var saving = dao().getTotal(C.SAVING)
            var expense = dao().getTotal(C.EXPENSE)

            callback(F.roundOff2Decimal( saving.total - expense.total))
        }
    }
}