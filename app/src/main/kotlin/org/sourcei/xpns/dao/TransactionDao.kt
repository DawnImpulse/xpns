package org.sourcei.xpns.dao

import android.arch.paging.DataSource
import android.arch.persistence.room.*
import org.sourcei.xpns.pojo.TransactionPojo

/**
 * @info -
 *
 * @author - Saksham
 * @note Last Branch Update - master
 *
 * @note Created on 2018-08-12 by Saksham
 * @note Updates :
 */
@Dao
interface TransactionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(transaction: TransactionPojo)

    @Query("SELECT * FROM transactions WHERE id=:id LIMIT 1")
    fun getItem(id: String): TransactionPojo

    @Query("SELECT * FROM transactions ORDER BY date ,id DESC")
    fun getItems(): DataSource.Factory<Int, TransactionPojo>

    @Delete
    fun deleteItem(transaction: TransactionPojo)

    @Query("DELETE FROM transactions WHERE id=:id")
    fun deleteItem(id: String)
}