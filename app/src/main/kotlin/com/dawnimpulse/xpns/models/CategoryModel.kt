package com.dawnimpulse.xpns.models

import android.arch.lifecycle.LiveData
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.content.Context
import com.dawnimpulse.xpns.dao.CategoryDao
import com.dawnimpulse.xpns.pojo.CategoryPojo
import com.dawnimpulse.xpns.utils.Config
import com.dawnimpulse.xpns.utils.XpnsRoom
import com.fasterxml.uuid.Generators
import kotlinx.coroutines.experimental.launch

/**
 * @info -
 *
 * @author - Saksham
 * @note Last Branch Update - master
 *
 * @note Created on 2018-08-17 by Saksham
 * @note Updates :
 */
class CategoryModel(private val context: Context) {

    // dao instance
    private fun dao(): CategoryDao {
        return XpnsRoom
                .getInstance(context, Config.DbName)
                .categoryDao()
    }

    // insert a new category
    fun insert(name: String, parent: String?, url: String, file: String?, type: String) {
        launch {
            dao().insert(
                    CategoryPojo(
                            Generators.timeBasedGenerator().generate().toString(),
                            name,
                            parent,
                            url,
                            file,
                            0,
                            type
                    )
            )
        }
    }

    // fetching a single item
    fun getItem(_id: String, callback: (CategoryPojo?) -> Unit) {
        launch {
            callback(dao().getItem(_id))
        }
    }

    // get all items paginated
    fun getItems(type: String): LiveData<PagedList<CategoryPojo>> {
        return LivePagedListBuilder(
                dao().getItems(type),
                PagedList.Config.Builder()
                        .setPageSize(30)
                        .setEnablePlaceholders(true)
                        .build()
        ).build()
    }

    // get frequent items paginated
    fun getFrequentItems(type: String): LiveData<PagedList<CategoryPojo>> {
        return LivePagedListBuilder(
                dao().getFrequentItems(type),
                PagedList.Config.Builder()
                        .setPageSize(30)
                        .setEnablePlaceholders(true)
                        .build()
        ).build()
    }

    //update an item
    fun editItem(categoryPojo: CategoryPojo) {
        launch { dao().insert(categoryPojo) }
    }

    //delete an item
    fun deleteItem(categoryPojo: CategoryPojo) {
        launch { dao().deleteItem(categoryPojo) }
    }

    //delete an item
    fun deleteItem(_id: String) {
        launch { dao().deleteItem(_id) }
    }
}