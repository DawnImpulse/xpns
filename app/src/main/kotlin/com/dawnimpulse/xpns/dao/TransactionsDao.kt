package com.dawnimpulse.xpns.dao

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
public interface TransactionsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(transaction: TransactionPojo)

    @Query("Select * from TRANSACTIONS where _id=:_id")
    fun getItem(_id: String): List<TransactionPojo>

    @Query("Select * from TRANSACTIONS order by DATE desc limit :limit offset :page")
    fun getItems(page: Int,limit:Int): List<TransactionPojo>

    @Query("Select * from TRANSACTIONS")
    fun getAll(): List<TransactionPojo>?

    @Delete
    fun deleteItem(transaction: TransactionPojo)
}