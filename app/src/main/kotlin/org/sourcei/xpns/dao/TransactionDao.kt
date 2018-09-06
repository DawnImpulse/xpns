package org.sourcei.xpns.dao

import android.arch.paging.DataSource
import android.arch.persistence.room.*
import org.sourcei.xpns.pojo.TransactionCPojo
import org.sourcei.xpns.pojo.TransactionPojo

/**
 * @info -
 *
 * @author - Saksham
 * @tnote Last Branch Update - master
 *
 * @tnote Created on 2018-08-12 by Saksham
 * @tnote Updates :
 */
@Dao
interface TransactionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(transaction: TransactionPojo)

    @Query("SELECT t.*,c.* FROM transactions t INNER JOIN category c ON tcid = cid WHERE tid=:id LIMIT 1")
    fun getItem(id: String): TransactionCPojo

    //@Query("SELECT * FROM transactions ORDER BY tdate ,tcid DESC")
    //@Query("SELECT t.aid as aid,t.tcid as tcid, t.tamount as tamount,t.tsyncState as tsyncState, t.tdate as tdate,t.tnote as tnote,c.cicon as cicon,c.ctype as ctype,c.ccolor as ccolor,c.cname as cname FROM transactions t INNER JOIN category c ON t.tcid = c.tcid ORDER BY t.tdate,t.aid DESC")
    @Query("SELECT t.*,c.* FROM transactions t INNER JOIN category c ON tcid = c.cid ORDER BY tdate,taid DESC")
    fun getItems(): DataSource.Factory<Int, TransactionCPojo>

    @Delete
    fun deleteItem(transaction: TransactionPojo)

    @Query("DELETE FROM transactions WHERE tid=:id")
    fun deleteItem(id: String)
}