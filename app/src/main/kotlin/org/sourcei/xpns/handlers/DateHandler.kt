package org.sourcei.xpns.handlers

import android.annotation.SuppressLint
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
 * Saksham - 2018 09 12 - master - convert MM -> MMMM
 */
@SuppressLint("SimpleDateFormat")
object DateHandler {

    // convert number month to String
    fun convertMMtoMMMM(month: String): String {
        val sdf = SimpleDateFormat("MM")
        val sdfN = SimpleDateFormat("MMMM")
        return sdfN.format(sdf.parse(month))
    }

    // convert for add transaction layout
    fun convertAddTLayout(y: Int, m: Int, d: Int): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val sdfN = SimpleDateFormat("dd MMM '`'yy")
        return sdfN.format(sdf.parse("$y-${m + 1}-$d"))
    }

    // convert for transaction saving
    fun convertAddTLayout(date: String): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val sdfN = SimpleDateFormat("dd MMM '`'yy")
        return sdf.format(sdfN.parse(date))
    }

    // convert tdate & time strings to java.util.Date object
    fun fullToDate(date: String, time: String): Date {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'hh:mm")
        return convertUtilToSql(sdf.parse("${date}T$time"))
    }

    // convert stored date to visible
    fun convertStoredToVisible(date: String): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val sdfN = SimpleDateFormat("dd MMM '`'yy")
        return sdfN.format(sdf.parse(date))
    }

    // get viewable date from Date object
    fun viewableFromObject(date: Date): String {
        val sdfN = SimpleDateFormat("dd MMM '`'yy")
        return sdfN.format(convertSqlToUtil(date))
    }

    // get viewable time from Date object
    fun viewableTimeFromObject(date: Date): String {
        val sdfN = SimpleDateFormat("hh:mm")
        return sdfN.format(convertSqlToUtil(date))
    }


    // convert util tdate to sql tdate
    private fun convertUtilToSql(date: java.util.Date): Date {
        return Date(date.time)
    }

    // convert sql date to util date
    private fun convertSqlToUtil(date: Date): java.util.Date {
        return java.util.Date(date.time)
    }
}