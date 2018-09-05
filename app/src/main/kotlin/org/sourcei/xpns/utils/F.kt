package org.sourcei.xpns.utils

import java.sql.Date
import java.text.SimpleDateFormat

/**
 * @info - creating simple as well as member functions
 *
 * @author - Saksham
 * @note Last Branch Update - master
 *
 * @note Created on 2018-08-15 by Saksham
 * @note Updates :
 */
object F {
    // convert date & time strings to java.util.Date object
    fun toDate(date: String, time: String): Date {
        var sdf = SimpleDateFormat("yyyy-MM-dd'T'hh:mm")
        return convertUtilToSql(sdf.parse("${date}T$time"))
    }

    // convert util date to sql date
    fun convertUtilToSql(date: java.util.Date): Date {
        var sDate = Date(date.time);
        return sDate;
    }

    // round off a number to two decimals
    fun roundOff2Decimal(number: Double): Double {
        return Math.round(number * 100.0) / 100.0
    }

}