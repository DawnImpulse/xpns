package org.sourcei.xpns.convertors

import android.arch.persistence.room.TypeConverter
import java.sql.Date


/**
 * @info -
 *
 * @author - Saksham
 * @note Last Branch Update - master
 *
 * @note Created on 2018-08-18 by Saksham
 * @note Updates :
 */
class DateConvertor {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return (date?.time)!!.toLong()
    }
}