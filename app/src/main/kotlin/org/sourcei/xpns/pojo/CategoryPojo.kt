package org.sourcei.xpns.pojo

import android.arch.persistence.room.Entity

/**
 * @info -
 *
 * @author - Saksham
 * @note Last Branch Update - master
 *
 * @note Created on 2018-08-17 by Saksham
 * @note Updates :
 */
@Entity(
        tableName = "category",
        primaryKeys = ["id"]
)
data class CategoryPojo(
        var id: String, //unique id time based
        var name: String, //name of the category
        var parent: String?, //parent category _id if exists
        var icon: IconPojo, //icon pojo
        var frequency: Int, //number of times category is frequency
        var type: String, //saving , expense, bill etc
        var color: String //additional color input
)