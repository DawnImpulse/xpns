package org.sourcei.xpns.convertors

import androidx.room.TypeConverter
import java.sql.Date


/**
 * @info -
 *
 * @author - Saksham
 * @tnote Last Branch Update - master
 *
 * @tnote Created on 2018-08-18 by Saksham
 * @tnote Updates :
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