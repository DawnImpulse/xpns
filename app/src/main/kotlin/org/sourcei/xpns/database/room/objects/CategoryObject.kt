package org.sourcei.xpns.database.room.objects

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import org.sourcei.xpns.database.realtime.objects.IconPojo

/**
 * @info -
 *
 * @author - Saksham
 * @tnote Last Branch Update - master
 *
 * @tnote Created on 2018-08-17 by Saksham
 * @tnote Updates :
 */
@Entity(
    tableName = "category",
    indices = [Index(value = ["cid"], unique = true)]
)
data class CategoryObject(
    @PrimaryKey(autoGenerate = true)
    var caid: Int, //auto increment caid
    var cid: String, // uuid
    var cname: String, //cname of the category
    var cisParent: Boolean, //is given category is a parent
    var cisChild: Boolean, //is given category a child
    var cParent: String?, //parent id
    var cchildren: ArrayList<String>?, //list of ids of children if it is parent
    var cicon: IconPojo, //cicon pojo
    var cfrequency: Int, //number of times category is cfrequency
    var ctype: String, //saving , expense, bill etc
    var ccolor: String, //additional ccolor input
    var csyncState: Boolean //if category is synced
)