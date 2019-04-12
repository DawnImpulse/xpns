package org.sourcei.xpns.utils

import android.content.Context
import android.content.SharedPreferences

/**
 * @info - used to create shared preference methods
 *
 * @author - Saksham
 * @tnote Last Branch Update - master
 *
 * @tnote Created on 2018-08-15 by Saksham
 * @tnote Updates :
 */
class P(context: Context) {
    private var prefs: SharedPreferences = context.getSharedPreferences(C.DEFAULT, Context.MODE_PRIVATE)

    //get active database
    fun getActiveDb(): String {
        return if (prefs.contains(C.ACTIVE_DB))
            prefs.getString(C.ACTIVE_DB, "")!!
        else
            C.MAIN_DB
    }
}