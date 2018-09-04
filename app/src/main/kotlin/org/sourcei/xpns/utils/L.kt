/*
ISC License

Copyright 2018, Saksham (DawnImpulse)

Permission to use, copy, modify, and/or distribute this software for any purpose with or without fee is hereby granted,
provided that the above copyright notice and this permission notice appear in all copies.

THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES WITH REGARD TO THIS SOFTWARE INCLUDING ALL
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY SPECIAL, DIRECT,
INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS,
WHETHER IN AN ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION WITH THE USE
OR PERFORMANCE OF THIS SOFTWARE.*/
package com.dawnimpulse.wallup.utils

import android.util.Log
import com.google.gson.Gson
import org.sourcei.xpns.BuildConfig
import org.sourcei.xpns.utils.C

/**
 * @author Saksham
 *
 * @note Last Branch Update -
 * @note Created on 2018-05-20 by Saksham
 *
 * @note Updates :
 */
object L {

    /**
     * debug message
     * @param filename
     * @param message
     */
    fun d(filename: String, message: Any) {
        if (BuildConfig.DEBUG)
            Log.d(C.XPNS, "$filename :: $message")
    }

    /**
     * debug object
     * @param filename
     * @param message
     */
    fun dO(filename: String, message: Any) {
        if (BuildConfig.DEBUG)
            Log.d(C.XPNS, "$filename :: ${Gson().toJson(message)}")
    }

    /**
     * error message
     * @param filename
     * @param message
     */
    fun e(filename: String, message: Any) {
        if (BuildConfig.DEBUG)
            Log.d(C.XPNS, "$filename :: $message")
    }

    /**
     * error object
     * @param filename
     * @param message
     */
    fun eO(filename: String, message: Any) {
        if (BuildConfig.DEBUG)
            Log.d(C.XPNS, "$filename :: ${Gson().toJson(message)}")
    }

    /**
     * warn message
     * @param filename
     * @param message
     */
    fun w(filename: String, message: Any) {
        if (BuildConfig.DEBUG)
            Log.d(C.XPNS, "$filename :: $message")
    }

    /**
     * warn object
     * @param filename
     * @param message
     */
    fun wO(filename: String, message: Any) {
        if (BuildConfig.DEBUG)
            Log.d(C.XPNS, "$filename :: ${Gson().toJson(message)}")
    }
}