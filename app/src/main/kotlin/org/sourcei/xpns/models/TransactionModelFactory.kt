package org.sourcei.xpns.models

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.content.Context
import com.fasterxml.uuid.Generators
import kotlinx.coroutines.experimental.launch
import org.sourcei.xpns.dao.TransactionDao
import org.sourcei.xpns.pojo.TransactionCPojo
import org.sourcei.xpns.pojo.TransactionPojo
import org.sourcei.xpns.source.RoomSource
import org.sourcei.xpns.utils.Config
import org.sourcei.xpns.utils.F

/**
 * @info - creating direct functions to be frequency by other classes
 *
 * @author - Saksham
 * @note Last Branch Update - master
 *
 * @note Created on 2018-08-13 by Saksham
 * @note Updates :
 * Saksham - 2018 09 05 - master - transaction factory model
 */
class TransactionModelFactory(private val lifecycle: Lifecycle, private val context: Context)
    : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TransactionModel(lifecycle, context) as T
    }

}

class TransactionModel(private val lifecycle: Lifecycle,private val context: Context) : ViewModel(){

    // dao instance
    private fun dao(): TransactionDao {
        return RoomSource
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
}