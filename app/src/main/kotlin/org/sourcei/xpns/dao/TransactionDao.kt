package org.sourcei.xpns.dao

import android.arch.paging.DataSource
import android.arch.persistence.room.*
import org.sourcei.xpns.pojo.TransactionCPojo
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

    @Query("SELECT t.id as id, t.amount as amount,t.syncState as syncState, t.date as date,t.note as note,c.icon as icon,c.type as type,c.color as color,c.name as name FROM transactions t INNER JOIN category c ON t.cid = c.id WHERE t.id=:id LIMIT 1")
    fun getItem(id: String): TransactionCPojo

    //@Query("SELECT * FROM transactions ORDER BY date ,id DESC")
    @Query("SELECT t.id as id, t.amount as amount,t.syncState as syncState, t.date as date,t.note as note,c.icon as icon,c.type as type,c.color as color,c.name as name FROM transactions t INNER JOIN category c ON t.cid = c.id")
    fun getItems(): DataSource.Factory<Int, TransactionCPojo>

    @Delete
    fun deleteItem(transaction: TransactionPojo)

    @Query("DELETE FROM transactions WHERE id=:id")
    fun deleteItem(id: String)
}