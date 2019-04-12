package org.sourcei.xpns.utils.convertors

import androidx.room.TypeConverter
import java.util.*


/**
 * @info -
 *
 * @author - Saksham
 * @tnote Last Branch Update - master
 *
 * @tnote Created on 2018-08-18 by Saksham
 * @tnote Updates :
 *  Saksham - 2019 02 16 - master - converted sql date -> util date
 */
class DateConvertor {
    private val NAME = "DateConvertor"

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null)
            null
        else {
            Date(value)

            /*val cal = Calendar.getInstance()
            cal.timeInMillis = value
            return cal.time*/

        }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return (date?.time)!!.toLong()
    }
}