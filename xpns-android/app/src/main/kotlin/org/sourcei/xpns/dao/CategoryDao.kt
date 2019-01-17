package org.sourcei.xpns.dao

import androidx.paging.DataSource
import androidx.room.*
import org.sourcei.xpns.pojo.CategoryPojo

/**
 * @info -
 *
 * @author - Saksham
 * @tnote Last Branch Update - master
 *
 * @tnote Created on 2018-08-17 by Saksham
 * @tnote Updates :
 */
@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(categoryPojo: CategoryPojo)

    @Query("SELECT * FROM category WHERE cid=:id LIMIT 1")
    fun getItem(id: String): CategoryPojo

    @Query("SELECT * FROM category WHERE ctype=:type ORDER BY cname")
    fun getItems(type: String): DataSource.Factory<Int, CategoryPojo>

    @Query("SELECT * FROM category WHERE ctype=:type ORDER BY cfrequency DESC")
    fun getFrequentItems(type: String): DataSource.Factory<Int, CategoryPojo>

    @Delete
    fun deleteItem(categoryPojo: CategoryPojo)

    @Query("DELETE FROM category WHERE cid=:id")
    fun deleteItem(id: String)
}