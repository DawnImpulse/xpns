package org.sourcei.xpns.dao

import android.arch.paging.DataSource
import android.arch.persistence.room.*
import org.sourcei.xpns.pojo.CategoryPojo

/**
 * @info -
 *
 * @author - Saksham
 * @note Last Branch Update - master
 *
 * @note Created on 2018-08-17 by Saksham
 * @note Updates :
 */
@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(categoryPojo: CategoryPojo)

    @Query("SELECT * FROM category WHERE id=:id LIMIT 1")
    fun getItem(id: String): CategoryPojo

    @Query("SELECT * FROM category WHERE type=:type ORDER BY name")
    fun getItems(type: String): DataSource.Factory<Int, CategoryPojo>

    @Query("SELECT * FROM category WHERE type=:type ORDER BY frequency DESC")
    fun getFrequentItems(type: String): DataSource.Factory<Int, CategoryPojo>

    @Delete
    fun deleteItem(categoryPojo: CategoryPojo)

    @Query("DELETE FROM category WHERE id=:id")
    fun deleteItem(id: String)
}