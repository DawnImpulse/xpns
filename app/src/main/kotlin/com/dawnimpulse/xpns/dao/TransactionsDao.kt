package com.dawnimpulse.xpns.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.dawnimpulse.xpns.pojo.Transactions

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
public interface TransactionsDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(transaction:Transactions)

    @Query("Select * from TRANSACTIONS")
    fun fetchAll() : List<Transactions>
}