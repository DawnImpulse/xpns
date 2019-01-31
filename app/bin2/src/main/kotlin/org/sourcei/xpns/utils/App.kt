package org.sourcei.xpns.utils

import android.app.Application
import com.amitshekhar.DebugDB
import com.google.firebase.database.FirebaseDatabase
import org.sourcei.xpns.R
import uk.co.chrisjenx.calligraphy.CalligraphyConfig


/**
 * @info -
 *
 * @author - Saksham
 * @tnote Last Branch Update - master
 *
 * @tnote Created on 2018-08-10 by Saksham
 * @tnote Updates :
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        DebugDB.getAddressLog();
        Config.DbName = P(this).getActiveDb()
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        setFonts()
    }

    /**
     * Set fonts
     */
    private fun setFonts() {
        CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
                .setDefaultFontPath("font/product_sans.xml")
                .setFontAttrId(R.attr.fontPath)
                .build())
    }
}