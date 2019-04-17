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

import org.sourcei.xpns.database.realtime.objects.IconPojo
import org.sourcei.xpns.database.room.objects.CategoryObject
import org.sourcei.xpns.database.room.objects.TransactionPojo
import org.sourcei.xpns.ui.objects.ViewTransactionObject
import org.sourcei.xpns.utils.C
import org.sourcei.xpns.utils.extensions.jsonOf
import org.sourcei.xpns.utils.others.Observe

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

    //---------------------------------
    //--------- TRANSACTION -----------

    // new transaction added
    fun newTransaction(pojo: TransactionPojo) {
        Event.send(
            jsonOf(
                Pair(C.TYPE, C.NEW_TRANSACTION),
                Pair(C.TRANSACTION, Observe(pojo))
            )
        )
    }

    // get single transaction
    fun singleTransaction(pojo: ViewTransactionObject) {
        Event.send(
            jsonOf(
                Pair(C.TYPE, C.SINGLE_TRANSACTION),
                Pair(C.TRANSACTION, Observe(pojo))
            )
        )
    }

    // get multiple transactions
    fun multipleTransactions(list: List<ViewTransactionObject>) {
        Event.send(
            jsonOf(
                Pair(C.TYPE, C.MULTIPLE_TRANSACTION),
                Pair(C.TRANSACTIONS, list.map { Observe(it) })
            )
        )
    }

    // edit transaction
    fun editTransaction(pojo: TransactionPojo) {
        Event.send(
            jsonOf(
                Pair(C.TYPE, C.EDIT_TRANSACTION),
                Pair(C.TRANSACTION, Observe(pojo))
            )
        )
    }

    // edit transaction
    fun deleteTransaction(tid: String) {
        Event.send(
            jsonOf(
                Pair(C.TYPE, C.DELETE_TRANSACTION),
                Pair(C.TRANSACTION, tid)
            )
        )
    }

    //---------------------------------
    //----------- ICONS ---------------

    // get all icons
    fun allIcons(error: Any?, icons: List<IconPojo>?) {
        Event.send(
            jsonOf(
                Pair(C.TYPE, C.ICONS),
                if (error != null)
                    Pair(C.ERROR, error)
                else
                    Pair(C.ICONS, icons!!.map { Observe(it) })

            )
        )
    }


    //---------------------------------
    //---------- CATEGORY -------------

    // new category
    fun newCategory(pojo: CategoryObject) {
        Event.send(
            jsonOf(
                Pair(C.TYPE, C.NEW_CATEGORY),
                Pair(C.CATEGORY, Observe(pojo))
            )
        )
    }

    // get single category
    fun singleCategory(pojo: CategoryObject) {
        Event.send(
            jsonOf(
                Pair(C.TYPE, C.SINGLE_CATEGORY),
                Pair(C.CATEGORY, Observe(pojo))
            )
        )
    }

    // get multiple category
    fun multipleCategories(list: List<CategoryObject>) {
        Event.send(
            jsonOf(
                Pair(C.TYPE, C.MULTIPLE_CATEGORY),
                Pair(C.CATEGORIES, list.map { Observe(it) })
            )
        )
    }

    // edit category
    fun editCategory(pojo: CategoryObject) {
        Event.send(
            jsonOf(
                Pair(C.TYPE, C.EDIT_CATEGORY),
                Pair(C.CATEGORY, Observe(pojo))
            )
        )
    }

    // delete category
    fun deleteCategory(cid: String) {
        Event.send(
            jsonOf(
                Pair(C.TYPE, C.DELETE_CATEGORY),
                Pair(C.CATEGORY, cid)
            )
        )
    }
}