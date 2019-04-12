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
package org.sourcei.xpns.utils.events

import org.sourcei.xpns.database.room.objects.TransactionPojo
import org.sourcei.xpns.utils.C
import org.sourcei.xpns.utils.extensions.jsonOf

/**
 * @info - send various cross application events
 *
 * @author - Saksham
 * @note Last Branch Update - master
 *
 * @note Created on 2019-04-12 by Saksham
 * @note Updates :
 */
object Post {

    // new transaction added
    fun newTransaction(pojo: TransactionPojo) {
        Event.send(
            jsonOf(
                Pair(C.TYPE, C.NEW_TRANSACTION),
                Pair(C.TRANSACTION, pojo)
            )
        )
    }

    // new
}