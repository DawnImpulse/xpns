package org.sourcei.xpns.utils

import android.content.Context
import android.widget.Toast

/**
 * @info -
 *
 * @author - Saksham
 * @note Last Branch Update - master
 *
 * @note Created on 2019-01-17 by Saksham
 * @note Updates :
 */
fun Context.toast(message: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this,message,length).show()
}