package org.sourcei.xpns.pojo

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

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
data class CategoryPojo(
        @PrimaryKey(autoGenerate = true)
        var caid: Int, //auto increment tcid
        var cid: String, // uuid
        var cname: String, //cname of the category
        var cisParent: Boolean, //is given category is a parent
        var cchilden: List<String>?, //list of ids of children if it is parent
        var cicon: IconPojo, //cicon pojo
        var cfrequency: Int, //number of times category is cfrequency
        var ctype: String, //saving , expense, bill etc
        var ccolor: String, //additional ccolor input
        var csyncState: Boolean //if category is synced
)