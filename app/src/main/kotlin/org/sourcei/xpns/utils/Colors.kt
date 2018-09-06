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

    fun EXPENSE():Int{
        return ContextCompat.getColor(context, R.color.expense)
    }

    fun SAVING():Int{
        return ContextCompat.getColor(context, R.color.saving)
    }
}