package org.sourcei.xpns.models

import android.arch.lifecycle.*
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.content.Context
import com.fasterxml.uuid.Generators
import kotlinx.coroutines.experimental.launch
import org.sourcei.xpns.dao.CategoryDao
import org.sourcei.xpns.pojo.CategoryPojo
import org.sourcei.xpns.pojo.IconPojo
import org.sourcei.xpns.source.RoomSource
import org.sourcei.xpns.utils.Config

/**
 * @info -
 *
 * @author - Saksham
 * @note Last Branch Update - master
 *
 * @note Created on 2018-08-17 by Saksham
 * @note Updates :
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
    fun insert(name: String, parent: String?, icon: IconPojo,
               file: String?, type: String, color: String) {
        launch {
            dao().insert(
                    CategoryPojo(
                            Generators.timeBasedGenerator().generate().toString(),
                            name,
                            parent,
                            icon,
                            file,
                            0,
                            type,
                            color
                    )
            )
        }
    }

    // fetching a single item
    fun getItem(id: String, callback: (CategoryPojo?) -> Unit) {
        launch {
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
        launch { dao().insert(categoryPojo) }
    }

    //delete an item
    fun deleteItem(categoryPojo: CategoryPojo) {
        launch { dao().deleteItem(categoryPojo) }
    }

    //delete an item
    fun deleteItem(id: String) {
        launch { dao().deleteItem(id) }
    }
}