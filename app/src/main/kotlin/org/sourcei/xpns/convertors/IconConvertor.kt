package org.sourcei.xpns.convertors

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import org.sourcei.xpns.pojo.IconPojo


/**
 * @info -
 *
 * @author - Saksham
 * @note Last Branch Update - master
 *
 * @note Created on 2018-08-18 by Saksham
 * @note Updates :
 */
class IconConvertor {
    @TypeConverter
    fun fromString(value: String?): IconPojo? {
        return if (value == null) null else Gson().fromJson(value, IconPojo::class.java)
    }

    @TypeConverter
    fun stringToIcon(icon: IconPojo?): String? {
        return Gson().toJson(icon!!)
    }
}