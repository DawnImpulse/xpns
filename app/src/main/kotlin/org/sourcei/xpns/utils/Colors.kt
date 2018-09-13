package org.sourcei.xpns.utils

import android.content.Context
import android.support.v4.content.ContextCompat
import org.sourcei.xpns.R

/**
 * @info -
 *
 * @author - Saksham
 * @note Last Branch Update - master
 *
 * @note Created on 2018-09-06 by Saksham
 * @note Updates :
 */
// returning colors
class Colors(private val context: Context) {
    val EXPENSE = ContextCompat.getColor(context, R.color.expense)
    val SAVING = ContextCompat.getColor(context, R.color.saving)
    val BLACK = ContextCompat.getColor(context, R.color.black)
    val WHITE = ContextCompat.getColor(context, R.color.white)
    val GREY_300 = ContextCompat.getColor(context, R.color.grey300)
    val GREY_400 = ContextCompat.getColor(context, R.color.grey400)
    val GREY_500 = ContextCompat.getColor(context, R.color.grey500)


}