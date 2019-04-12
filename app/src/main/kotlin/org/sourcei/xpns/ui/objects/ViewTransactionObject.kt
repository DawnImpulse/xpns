package org.sourcei.xpns.ui.objects

import androidx.room.Embedded
import org.sourcei.xpns.database.room.objects.CategoryObject
import org.sourcei.xpns.database.room.objects.TransactionPojo


/**
 * @info -
 *
 * @author - Saksham
 * @tnote Last Branch Update - master
 *
 * @tnote Created on 2018-09-05 by Saksham
 * @tnote Updates :
 */

data class ViewTransactionObject(
        @Embedded
        var obj: TransactionPojo,
        @Embedded
        var cat: CategoryObject
)