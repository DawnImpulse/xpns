package org.sourcei.xpns.convertors

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


/**
 * @info -
 *
 * @author - Saksham
 * @note Last Branch Update - master
 *
 * @note Created on 2019-01-19 by Saksham
 * @note Updates :
 */
class ListConvertor {
    @TypeConverter
    fun fromString(value: String): ArrayList<String>? {
        return if (value.isNotEmpty()) {
            val listType = object : TypeToken<ArrayList<String>>() {}.type
            Gson().fromJson(value, listType)
        } else
            null
    }

    @TypeConverter
    fun fromArrayList(list: ArrayList<String>?): String {
        return if (list != null)
            Gson().toJson(list)
        else
            ""
    }
}