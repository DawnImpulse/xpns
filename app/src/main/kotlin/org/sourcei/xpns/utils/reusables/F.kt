package org.sourcei.xpns.utils

/**
 * @info - creating simple as well as member functions
 *
 * @author - Saksham
 * @tnote Last Branch Update - master
 *
 * @tnote Created on 2018-08-15 by Saksham
 * @tnote Updates :
 */
object F {

    // round off a number to two decimals
    fun roundOff2Decimal(number: Double): Double {
        return Math.round(number * 100.0) / 100.0
    }

    // append zero in front if length < 2
    fun addZero(string: String): String {
        return if (string.length < 2)
            "0$string"
        else
            string
    }

}