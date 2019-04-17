package org.sourcei.xpns.database.room.models


import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.sourcei.xpns.database.realtime.objects.IconPojo
import org.sourcei.xpns.database.room.RoomSource
import org.sourcei.xpns.database.room.dao.CategoryDao
import org.sourcei.xpns.database.room.objects.CategoryObject
import org.sourcei.xpns.ui.activities.ModularActivity
import org.sourcei.xpns.utils.events.Post
import org.sourcei.xpns.utils.extensions.uuid
import org.sourcei.xpns.utils.others.Config
import org.sourcei.xpns.utils.others.Lifecycle

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
class CategoryModel(private var activity: ModularActivity) : ViewModel() {

    // dao instance
    private fun dao(): CategoryDao {
        return RoomSource
            .getInstance(activity.context, Config.DbName)
            .categoryDao()
    }

    // insert a new category
    fun insert(
        name: String, icon: IconPojo, type: String, color: String,
        parent: String? = null, isParent: Boolean = true, isChild: Boolean = false
    ) {

        GlobalScope.launch {

            val obj = CategoryObject(
                0,
                uuid(),
                name,
                isParent,
                isChild,
                parent,
                null,
                icon,
                0,
                type,
                color,
                false
            )

            // inserting in db
            dao().insert(obj)

            // send event on UI (on start)
            Lifecycle.onStart(activity) {
                Post.newCategory(obj)
            }
        }
    }

    // fetching a single item
    fun getItem(cid: String) {
        GlobalScope.launch {
            val item = dao().getItem(cid)

            // send event on UI (on start)
            Lifecycle.onStart(activity){
                Post.singleCategory(item)
            }
        }
    }

    // get all items paginated
    fun getItems(type: String) {
        GlobalScope.launch {
            val items = dao().getItems(type)

            // send event on UI (on start)
            Lifecycle.onStart(activity){
                Post.multipleCategories(items)
            }
        }
    }

    // update an item
    fun editItem(categoryObject: CategoryObject) {
        GlobalScope.launch {

            dao().insert(categoryObject)

            Lifecycle.onStart(activity){
                Post.editCategory(categoryObject)
            }
        }
    }

    //delete an item
    fun deleteItem(cid: String) {
        GlobalScope.launch {

            dao().deleteItem(cid)

            // send event on UI (on start)
            Lifecycle.onStart(activity){
                Post.deleteCategory(cid)
            }
        }
    }
}