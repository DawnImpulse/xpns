package org.sourcei.xpns.models

import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import android.content.Context
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.sourcei.xpns.dao.CategoryDao
import org.sourcei.xpns.pojo.CategoryPojo
import org.sourcei.xpns.pojo.IconPojo
import org.sourcei.xpns.source.RoomSource
import org.sourcei.xpns.utils.Config
import java.util.*

/**
 * @info -
 *
 * @author - Saksham
 * @tnote Last Branch Update - master
 *
 * @tnote Created on 2018-08-17 by Saksham
 * @tnote Updates :
 *  Saksham - 2018 08 17 - master - adding lifecycle
 */
class CategoryModelFactory(private val lifecycle: Lifecycle, private val context: Context)
    : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CategoryModel(lifecycle, context) as T
    }

}


class CategoryModel(private val lifecycle: Lifecycle, private val context: Context) : ViewModel() {

    // dao instance
    private fun dao(): CategoryDao {
        return RoomSource
                .getInstance(context, Config.DbName)
                .categoryDao()
    }

    // insert a new category
    fun insert(name: String, parent: String?, icon: IconPojo, type: String, color: String) {
        GlobalScope.launch {
            dao().insert(
                    CategoryPojo(
                            0,
                            UUID.randomUUID().toString(),
                            name,
                            parent,
                            icon,
                            0,
                            type,
                            color,
                            false
                    )
            )
        }
    }

    // fetching a single item
    fun getItem(id: String, callback: (CategoryPojo?) -> Unit) {
        GlobalScope.launch {
            lifecycle.addObserver(object : LifecycleObserver {
                @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
                fun onResume() {
                    callback(dao().getItem(id))
                }
            })
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
        GlobalScope.launch { dao().insert(categoryPojo) }
    }

    //delete an item
    fun deleteItem(categoryPojo: CategoryPojo) {
        GlobalScope.launch { dao().deleteItem(categoryPojo) }
    }

    //delete an item
    fun deleteItem(id: String) {
        GlobalScope.launch { dao().deleteItem(id) }
    }
}