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

    // getting a single category
    @Query("SELECT * FROM category WHERE cid=:id LIMIT 1")
    fun getItem(id: String): CategoryPojo

    // list of all categories ordered by name , filtered by type
    @Query("SELECT * FROM category WHERE ctype=:type AND cisParent=1 ORDER BY cname")
    fun getItems(type: String): DataSource.Factory<Int, CategoryPojo>

    // list of all categories order by frequency, filtered by type
    @Query("SELECT * FROM category WHERE ctype=:type AND cisParent=1 ORDER BY cfrequency DESC")
    fun getFrequentItems(type: String): DataSource.Factory<Int, CategoryPojo>

    // delete an item
    @Delete
    fun deleteItem(categoryPojo: CategoryPojo)

    // delete an item by cid
    @Query("DELETE FROM category WHERE cid=:id")
    fun deleteItem(id: String)
}