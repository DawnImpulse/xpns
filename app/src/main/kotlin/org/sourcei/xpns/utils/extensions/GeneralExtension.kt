/**
 * ISC License
 *
 * Copyright 2018-2019, Saksham (DawnImpulse)
 *
 * Permission to use, copy, modify, and/or distribute this software for any purpose with or without fee is hereby granted,
 * provided that the above copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES WITH REGARD TO THIS SOFTWARE INCLUDING ALL
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY SPECIAL, DIRECT,
 * INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS,
 * WHETHER IN AN ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION WITH THE USE
 * OR PERFORMANCE OF THIS SOFTWARE.
 **/
package org.sourcei.xpns.utils.extensions

import com.google.gson.Gson
import org.json.JSONObject
import java.util.*

/**
 * @info -
 *
 * @author - Saksham
 * @note Last Branch Update - master
 *
 * @note Created on 2019-04-12 by Saksham
 * @note Updates :
 */
// json put params
fun jsonOf(vararg pairs: Pair<String, Any>) = JSONObject().apply {
    pairs.forEach {
        put(it.first, it.second)
    }
}

// array list
fun <T> arrayListOf(item: T): ArrayList<T> {
    val list = ArrayList<T>()
    list.add(item)
    return list
}

// to json
fun toJson(input: Any): String {
    return Gson().toJson(input)
}

// use to generate a unique uuid
fun uuid(): String {
    return UUID.randomUUID().toString()
}