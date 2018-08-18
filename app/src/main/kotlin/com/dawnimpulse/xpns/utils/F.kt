package com.dawnimpulse.xpns.utils

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
        return sdf.parse("${date}T$time") as Date
    }
}