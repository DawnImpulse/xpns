package org.sourcei.xpns.handlers

import java.sql.Date
import java.text.SimpleDateFormat

/**
 * @info -
 *
 * @author - Saksham
 * @note Last Branch Update - master
 *
 * @note Created on 2018-09-06 by Saksham
 * @note Updates :
 */
object DateHandler {
    // convert for add transaction layout
    fun convertAddTLayout(y: Int, m: Int, d: Int): String {
        var sdf = SimpleDateFormat("yyyy-MM-dd")
        var sdfN = SimpleDateFormat("dd MMM '`'yy")
        return sdfN.format(sdf.parse("$y-${m + 1}-$d"))
    }

    // convert for transaction saving
    fun convertAddTLayout(date:String): String {
        var sdf = SimpleDateFormat("yyyy-MM-dd")
        var sdfN = SimpleDateFormat("dd MMM '`'yy")
        return sdf.format(sdfN.parse(date))
    }

    // convert tdate & time strings to java.util.Date object
    fun fullToDate(date: String, time: String): Date {
        var sdf = SimpleDateFormat("yyyy-MM-dd'T'hh:mm")
        return convertUtilToSql(sdf.parse("${date}T$time"))
    }

    // convert util tdate to sql tdate
    private fun convertUtilToSql(date: java.util.Date): Date {
        var sDate = Date(date.time);
        return sDate;
    }
}