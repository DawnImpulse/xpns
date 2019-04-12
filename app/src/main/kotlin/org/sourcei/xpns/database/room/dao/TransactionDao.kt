package org.sourcei.xpns.database.room.dao

import androidx.room.*
import org.sourcei.xpns.ui.objects.ViewTransactionObject
import org.sourcei.xpns.database.room.objects.TransactionPojo
import org.sourcei.xpns.database.room.objects.TransactionTotal

/**
 * @info -
 *
 * @author - Saksham
 * @tnote Last Branch Update - master
 *
 * @tnote Created on 2018-08-12 by Saksham
 * @tnote Updates :
 * Saksham - 2018 09 12 - master - get balance / saving
 */
@Dao
interface TransactionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(transaction: TransactionPojo): Long

    @Query("SELECT t.*,c.* FROM transactions t INNER JOIN category c ON tcid = cid WHERE twallet=:wallet AND tid=:id LIMIT 1")
    fun getItem(id: String, wallet: String): ViewTransactionObject

    @Query("SELECT t.*,c.* FROM transactions t INNER JOIN category c ON tcid = c.cid WHERE twallet=:wallet ORDER BY tdate DESC")
    fun getItems(wallet: String): List<ViewTransactionObject>

    @Delete
    fun deleteItem(transaction: TransactionPojo)

    @Query("DELETE FROM transactions WHERE tid=:id")
    fun deleteItem(id: String)

    // get total balance
    @Query("Select SUM(tamount) AS total FROM transactions INNER JOIN category ON tcid = cid WHERE twallet=:wallet AND ctype=:type")
    fun getTotal(type: String, wallet: String): TransactionTotal
}