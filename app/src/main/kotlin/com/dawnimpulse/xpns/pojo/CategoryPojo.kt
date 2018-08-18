package com.dawnimpulse.xpns.pojo

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
        var url: String, //url to download icon from
        var file: String?, //path to the icon in storage if exists
        var frequency: Int, //number of times category is frequency
        var type: String //saving , expense, bill etc
)