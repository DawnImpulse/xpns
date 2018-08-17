package com.dawnimpulse.xpns.dao

import android.arch.paging.DataSource
import android.arch.persistence.room.*
import com.dawnimpulse.xpns.pojo.TransactionPojo

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

    @Query("SELECT * FROM transactions WHERE _id=:_id")
    fun getItem(_id: String): List<TransactionPojo>

    @Query("SELECT * FROM transactions ORDER BY date ,_id DESC")
    fun getItems(): DataSource.Factory<Int, TransactionPojo>

    @Delete
    fun deleteItem(transaction: TransactionPojo)

    @Query("DELETE FROM transactions WHERE _id=:_id")
    fun deleteItem(_id: String)
}