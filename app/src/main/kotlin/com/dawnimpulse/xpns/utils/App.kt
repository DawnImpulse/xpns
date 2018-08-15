package com.dawnimpulse.xpns.utils

import android.app.Application
import com.amitshekhar.DebugDB


/**
 * @info -
 *
 * @author - Saksham
 * @note Last Branch Update - master
 *
 * @note Created on 2018-08-10 by Saksham
 * @note Updates :
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        DebugDB.getAddressLog();
        Config.DbName = P(this).getActiveDb()
    }
}